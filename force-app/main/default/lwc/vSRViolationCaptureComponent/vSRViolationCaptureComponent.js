import { LightningElement, api, track } from 'lwc';
import { ShowToastEvent } from 'lightning/platformShowToastEvent';
import { NavigationMixin } from 'lightning/navigation';
import { notifyRecordUpdateAvailable } from 'lightning/uiRecordApi';

import getVSRViolationRecords from '@salesforce/apex/vSRViolationCaptureController.getVSRViolationRecords';
import getExistingVSRWithViolations from '@salesforce/apex/vSRViolationCaptureController.getExistingVSRWithViolations';
import submitVSR from '@salesforce/apex/vSRViolationCaptureController.submitVSR';

import ensureVsrForCase from '@salesforce/apex/vSRViolationCaptureController.ensureVsrForCase';
import ensureVillaDocumentForVsr from '@salesforce/apex/vSRViolationCaptureController.ensureVillaDocumentForVsr';
import createViolationWithPlaceholders from '@salesforce/apex/vSRViolationCaptureController.createViolationWithPlaceholders';
import ensureViolationPlaceholders from '@salesforce/apex/vSRViolationCaptureController.ensureViolationPlaceholders';
import createCarriedForwardViolationAndAttachAfter from '@salesforce/apex/vSRViolationCaptureController.createCarriedForwardViolationAndAttachAfter';
import deleteViolationAndFiles from '@salesforce/apex/vSRViolationCaptureController.deleteViolationAndFiles';
import updateViolationDocumentAfterUpload from '@salesforce/apex/vSRViolationCaptureController.updateViolationDocumentAfterUpload';

import upsertVillaAndAttach from '@salesforce/apex/vSRViolationCaptureController.upsertVillaAndAttach';
import getThumbnailUrls from '@salesforce/apex/vSRViolationCaptureController.getThumbnailUrls';

// NEW: remove/clear uploaded file (re-enables upload UI)
import clearDocumentFile from '@salesforce/apex/vSRViolationCaptureController.clearDocumentFile';

export default class VSRViolationCaptureComponent extends NavigationMixin(LightningElement) {
    @api recordId;

    // Metadata
    violations = [];

    // Search UI
    @track violationSearchText = '';
    @track showViolationDropdown = false;
    @track filteredViolationOptions = [];
    highlightedIndex = -1;
    selectedViolationId = null;

    _userFocusedSearch = false;
    _didInit = false;
    _outsideHandlerAdded = false;
    _blurTimer = null;

    // State
    @track rows = [];
    @track isParentMode = false;  // follow-up (Case.Related_Case__c exists)
    isBaselineMode = false;       // parent baseline vs reopen
    vsrId = null;
    _vsrWasDraftOnOpen = true;

    // Spinner
    isLoading = false;
    isSubmitting = false;
    isAddInProgress = false;
    _inflight = 0;

    get showGlobalSpinner() {
        return this.isLoading || this.isSubmitting || this._inflight > 0;
    }
    _beginWork() { this._inflight++; }
    _endWork() { this._inflight = Math.max(0, this._inflight - 1); }

    // Villa picture
    villaDocumentId = null;
    @track villaFiles = [];
    villaFilesSummary = '';
    villaFileUrl = null;
    villaThumbUrl = null;
    villaFileError = false;
    villaFileErrorMessage = '';

    // Pending deletes removed (we delete immediately on row delete)

    // Upload formats
    acceptedFormats = ['.png', '.jpg', '.jpeg', '.mp4', '.mov'];

    get villaUploadRecordId() {
        return this.villaDocumentId || this.vsrId;
    }

    get isVillaUploadDisabled() {
        return !this.villaUploadRecordId || this.showGlobalSpinner;
    }

    @api async submitFromHost() {
        return this.handleSubmit();
    }

    renderedCallback() {
        if (!this._didInit && this.recordId) {
            this._didInit = true;
            this.init();
        }

        if (this._outsideHandlerAdded) return;
        this._outsideHandlerAdded = true;

        this._handleOutsidePointerDown = (evt) => {
            if (!this.showViolationDropdown) return;
            const wrapper = this.template.querySelector('.searchPicklist');
            if (!wrapper) return;
            const path = evt.composedPath ? evt.composedPath() : [];
            if (!path.includes(wrapper)) {
                this.closeViolationDropdown();
                this._userFocusedSearch = false;
            }
        };

        document.addEventListener('pointerdown', this._handleOutsidePointerDown, true);
    }

    disconnectedCallback() {
        if (this._handleOutsidePointerDown) {
            document.removeEventListener('pointerdown', this._handleOutsidePointerDown, true);
        }
    }

    async init() {
        this.isLoading = true;
        try {
            // Ensure a single draft VSR exists for this Case (Status/Issued date remain null)
            this.vsrId = await ensureVsrForCase({ caseId: this.recordId });
            // Ensure villa placeholder exists so uploads target Documents__c (not VSR__c)
            try {
                this.villaDocumentId = await ensureVillaDocumentForVsr({ vsrId: this.vsrId });
            } catch (e) {
                // best-effort; loadExistingVsrRows will hydrate as well
            }
            await this.loadViolations();
            await this.loadExistingVsrRows();
            await this.hydrateAllThumbnails();
        } catch (e) {
            // eslint-disable-next-line no-console
            console.error(e);
        } finally {
            this.isLoading = false;
        }
    }

    // ---------------------------
    // Picklist/Search
    // ---------------------------
    get violationOptions() {
        return (this.violations || []).map(v => ({
            label: v.name,
            value: v.id
        }));
    }

    get isAddDisabled() {
        return !this.selectedViolationId || this.isAddInProgress;
    }

    handleViolationInputFocus() {
        this._userFocusedSearch = true;
        this.openViolationDropdown();
    }

    handleViolationInputBlur() {
        window.clearTimeout(this._blurTimer);
        this._blurTimer = window.setTimeout(() => {
            this._userFocusedSearch = false;
            this.closeViolationDropdown();
        }, 150);
    }

    openViolationDropdown() {
        this.showViolationDropdown = true;
        this.highlightedIndex = -1;
        this.applyViolationFilter();
    }

    closeViolationDropdown() {
        this.showViolationDropdown = false;
        this.highlightedIndex = -1;
    }

    get showClearViolation() {
        return !!(this.violationSearchText && this.violationSearchText.trim());
    }

    handleClearViolation(event) {
        event.preventDefault();
        event.stopPropagation();

        this.violationSearchText = '';
        this.selectedViolationId = null;
        this.highlightedIndex = -1;
        this.filteredViolationOptions = [];
        this.closeViolationDropdown();
    }

    handleViolationSearchChange(event) {
        this.violationSearchText = event.target.value || '';

        const selectedOpt = (this.violationOptions || []).find(o => o.value === this.selectedViolationId);
        const selectedLabel = selectedOpt ? (selectedOpt.label || '') : '';
        if (!this.violationSearchText || this.violationSearchText.trim() !== selectedLabel.trim()) {
            this.selectedViolationId = null;
        }

        if (this._userFocusedSearch) this.showViolationDropdown = true;
        this.highlightedIndex = -1;
        this.applyViolationFilter();
    }

    applyViolationFilter() {
        const q = (this.violationSearchText || '').trim().toLowerCase();
        const base = (this.violationOptions || []);
        let filtered = base;

        if (q) {
            filtered = base.filter(o =>
                (o.label || '').toLowerCase().includes(q) ||
                (o.value || '').toLowerCase().includes(q)
            );
        }

        this.filteredViolationOptions = filtered.map((o, idx) => ({
            ...o,
            _class: idx === this.highlightedIndex ? 'dropdownItem dropdownItemActive' : 'dropdownItem'
        }));
    }

    refreshHighlightedClasses() {
        this.filteredViolationOptions = (this.filteredViolationOptions || []).map((o, idx) => ({
            ...o,
            _class: idx === this.highlightedIndex ? 'dropdownItem dropdownItemActive' : 'dropdownItem'
        }));
    }

    handleViolationSelect(event) {
        window.clearTimeout(this._blurTimer);
        const value = event.currentTarget.dataset.value;
        const opt = (this.violationOptions || []).find(o => o.value === value);

        this.selectedViolationId = value;
        this.violationSearchText = opt ? opt.label : '';

        this.closeViolationDropdown();
    }

    handleViolationKeyDown = (event) => {
        if (!this.showViolationDropdown) this.showViolationDropdown = true;

        const max = (this.filteredViolationOptions || []).length - 1;

        switch (event.key) {
            case 'ArrowDown':
                event.preventDefault();
                this.highlightedIndex = Math.min(max, this.highlightedIndex + 1);
                this.refreshHighlightedClasses();
                break;
            case 'ArrowUp':
                event.preventDefault();
                this.highlightedIndex = Math.max(0, this.highlightedIndex - 1);
                this.refreshHighlightedClasses();
                break;
            case 'Enter':
                event.preventDefault();
                if (this.highlightedIndex >= 0 && this.filteredViolationOptions[this.highlightedIndex]) {
                    const selected = this.filteredViolationOptions[this.highlightedIndex];
                    this.selectedViolationId = selected.value;
                    this.violationSearchText = selected.label;
                    this.closeViolationDropdown();
                }
                break;
            case 'Escape':
                event.preventDefault();
                this.closeViolationDropdown();
                break;
            default:
                break;
        }
    };

    async loadViolations() {
        try {
            const data = await getVSRViolationRecords();

            this.violations = (data || []).map(r => ({
                id: r.Label,
                name: r.Heading__c || r.Label,
                category: r.Category__c || '',
                description: r.Description__c || '',
                developerName: r.DeveloperName
            }));

            this.applyViolationFilter();

        } catch (error) {
            // eslint-disable-next-line no-console
            console.error('Error loading Violation_Configuration__mdt:', error);
            this.violations = [];
            this.filteredViolationOptions = [];
        }
    }

    // ---------------------------
    // Load Existing VSR/Rows
    // ---------------------------
    async loadExistingVsrRows() {
        try {
            const jsonStr = await getExistingVSRWithViolations({ caseId: this.recordId });
            const data = JSON.parse(jsonStr || '{}');

            this.isParentMode = !!data.isFollowUp;
            this.isBaselineMode = !!data.sourceIsParentBaseline;
            this._vsrWasDraftOnOpen = !data.vsrStatus;

            // Villa doc (current case)
            this.villaDocumentId = data.villaDocumentId || this.villaDocumentId || null;
            const villaCdId = data.villaContentDocumentId || null;
            const villaName = data.villaFileName || '';

            if (villaCdId) {
                this.villaFiles = [{ name: villaName || 'Villa Picture', documentId: villaCdId }];
                this.villaFilesSummary = villaName || 'Villa Picture';
                this.villaFileUrl = `/lightning/r/ContentDocument/${villaCdId}/view`;
                this.villaFileError = false;
                this.villaFileErrorMessage = '';
            } else {
                this.villaFiles = [];
                this.villaFilesSummary = '';
                this.villaFileUrl = null;
                this.villaThumbUrl = null;
            }

            if (!data.hasVsr) {
                this.vsrId = null;
                this.rows = [];
                this.applyViolationFilter();
                return;
            }

            this.vsrId = data.vsrId;

            const loadedRows = (data.rows || []).map(r => {
                const name = r.name || '';
                const isOther = name.trim().toLowerCase() === 'other';

                if (this.isParentMode) {
                    const beforeCdId = r.beforeContentDocumentId || null;
                    const beforeName = r.beforeFileName || '';

                    const afterDocId = r.afterDocumentId || null;
                    const afterCdId = r.afterContentDocumentId || null;
                    const afterName = r.afterFileName || '';

                    const isCarriedForward = !!r.isCarriedForward;
                    const isFixedOnLoad = !!r.isFixedOnLoad;

                    const row = {
                        rowId: this.generateRowId(),

                        violationRecordId: r.violationRecordId || null,
                        sourceViolationRecordId: r.sourceViolationRecordId || null,

                        name,
                        category: r.category || '',
                        description: r.description || '',
                        isOther,

                        isCarriedForward,
                        isParentBaselineRow: !!r.isParentBaselineRow,
                        isFixedOnLoad,
                        isFixed: !!r.isFixed,

                        beforeDocumentId: r.beforeDocumentId || null,
                        beforeContentDocumentId: beforeCdId,
                        beforeFileName: beforeName,

                        beforeFiles: beforeCdId ? [{ name: beforeName || 'Before photo', documentId: beforeCdId }] : [],
                        beforeFilesSummary: beforeCdId ? (beforeName || 'Before photo') : '',
                        beforeFileUrl: beforeCdId ? `/lightning/r/ContentDocument/${beforeCdId}/view` : null,
                        beforeThumbUrl: null,

                        afterDocumentId: afterDocId,
                        afterFiles: afterCdId ? [{ name: afterName || 'After photo', documentId: afterCdId }] : [],
                        afterFilesSummary: afterCdId ? (afterName || 'After photo') : '',
                        afterFileUrl: afterCdId ? `/lightning/r/ContentDocument/${afterCdId}/view` : null,
                        afterThumbUrl: null,

                        isCategoryDisabled: false,
                        isBeforeDocNotReady: false,
                        isBeforeDocCreating: false,
                        // After upload can be done even without an afterDocumentId in baseline mode (we create records on upload)
                        isAfterDocNotReady: false,
                        isAfterDocCreating: false,
                        beforeUploadError: false,
                        beforeUploadErrorMessage: '',
                        afterFileError: false,
                        afterFileErrorMessage: ''
                    };

                    return this.deriveRowState(row);
                }

                // Evidence mode
                const contentDocumentId = r.contentDocumentId || null;
                const fileName = r.fileName || '';

                return {
                    rowId: this.generateRowId(),
                    violationRecordId: r.violationRecordId || null,

                    name,
                    category: r.category || '',
                    description: r.description || '',
                    isFixed: !!r.isFixed,
                    isOther,
                    isCategoryDisabled: false,

                    documentId: r.documentId || null,
                    evidenceUploadRecordId: (r.documentId || this.vsrId),
                    files: contentDocumentId ? [{ name: fileName || 'Uploaded file', documentId: contentDocumentId }] : [],
                    filesSummary: contentDocumentId ? (fileName || 'Uploaded file') : '',
                    fileUrl: contentDocumentId ? `/lightning/r/ContentDocument/${contentDocumentId}/view` : null,
                    fileThumbUrl: null,
                    fileError: false,
                    fileErrorMessage: '',
                    isDocNotReady: false,
                    isDocCreating: false
                };
            });

            this.rows = loadedRows;

            // Ensure every Violation has the 3 placeholders (Before/After/NOC) (best-effort).
            try {
                const vioIds = Array.from(new Set((this.rows || []).map(x => x?.violationRecordId).filter(Boolean)));
                if (vioIds.length) {
                    const map = await ensureViolationPlaceholders({ violationIds: vioIds });
                    this.rows = (this.rows || []).map(rr => {
                        if (!rr?.violationRecordId) return rr;
                        const p = map?.[rr.violationRecordId];
                        if (!p) return rr;
                        if (!this.isParentMode) {
                            const beforeId = p.BEFORE;
                            return {
                                ...rr,
                                documentId: beforeId || rr.documentId,
                                evidenceUploadRecordId: (beforeId || rr.documentId || this.vsrId)
                            };
                        }
                        return this.deriveRowState({
                            ...rr,
                            beforeDocumentId: p.BEFORE || rr.beforeDocumentId,
                            afterDocumentId: p.AFTER || rr.afterDocumentId
                        });
                    });
                }
            } catch (e) {
                // ignore
            }

            // Baseline rows still create child records on first AFTER upload.

            this.showViolationDropdown = false;
            this.highlightedIndex = -1;
            this._userFocusedSearch = false;
            this.applyViolationFilter();

        } catch (error) {
            // eslint-disable-next-line no-console
            console.error('Error preloading existing VSR:', error);
            this.vsrId = null;
            this.rows = [];
            this.isParentMode = false;
            this.isBaselineMode = false;
        }
    }

    // ---------------------------
    // Add Row
    // ---------------------------
    async handleAddRow() {
        await this.addRowFromSelectedViolation();
    }

    async addRowFromSelectedViolation() {
        if (!this.selectedViolationId) return;

        const opt = (this.violationOptions || []).find(o => o.value === this.selectedViolationId);
        if (!opt || (this.violationSearchText || '').trim() !== (opt.label || '').trim()) {
            this.selectedViolationId = null;
            return;
        }

        const violation = (this.violations || []).find(v => v.id === this.selectedViolationId);
        if (!violation) return;

        const rowId = this.generateRowId();

        const isOther =
            (violation.name || '').trim().toLowerCase() === 'other' ||
            (violation.id || '').trim().toLowerCase() === 'other';

        this.isAddInProgress = true;
        try {
            const created = await createViolationWithPlaceholders({
                caseId: this.recordId,
                vsrId: this.vsrId,
                heading: violation.name,
                category: isOther ? '' : violation.category,
                description: isOther ? '' : violation.description,
                isNewViolation: !!this.isParentMode
            });

            const violationId = created?.violationId || null;
            const beforeDocId = created?.beforeDocumentId || null;
            const afterDocId = created?.afterDocumentId || null;

            if (this.isParentMode) {
                const row = this.deriveRowState({
                    rowId,
                    violationRecordId: violationId,
                    sourceViolationRecordId: null,

                    name: violation.name,
                    category: isOther ? '' : violation.category,
                    description: isOther ? '' : violation.description,
                    isOther,

                    isCarriedForward: false,
                    isFixedOnLoad: false,
                    isFixed: false,

                    beforeDocumentId: beforeDocId,
                    beforeContentDocumentId: null,
                    beforeFileName: null,
                    beforeFiles: [],
                    beforeFilesSummary: '',
                    beforeFileUrl: null,
                    beforeThumbUrl: null,

                    afterDocumentId: afterDocId,
                    afterFiles: [],
                    afterFilesSummary: '',
                    afterFileUrl: null,
                    afterThumbUrl: null,

                    isCategoryDisabled: false,
                    isBeforeDocNotReady: false,
                    isBeforeDocCreating: false,
                    isAfterDocNotReady: false,
                    isAfterDocCreating: false,

                    beforeUploadError: false,
                    beforeUploadErrorMessage: '',
                    afterFileError: false,
                    afterFileErrorMessage: ''
                });

                this.rows = [...this.rows, row];
            } else {
                const row = {
                    rowId,
                    violationRecordId: violationId,

                    documentId: beforeDocId,
                    evidenceUploadRecordId: beforeDocId || this.vsrId,
                    name: violation.name,
                    category: isOther ? '' : violation.category,
                    description: isOther ? '' : violation.description,
                    isOther,

                    files: [],
                    filesSummary: '',
                    fileUrl: null,
                    fileThumbUrl: null,
                    fileError: false,
                    fileErrorMessage: '',

                    isDocNotReady: false,
                    isDocCreating: false,

                    isFixed: false,
                    isCategoryDisabled: false
                };

                this.rows = [...this.rows, row];
            }
        } catch (e) {
            this.toast('Add failed', this.reduceError(e), 'error');
        } finally {
            this.isAddInProgress = false;
        }
    }

    // ---------------------------
    // Deletes (rows)
    // ---------------------------
    async handleDeleteRow(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row) return;

        if (this.isParentMode && row.isDeleteDisabledFinal) return;

        // If the row has a saved violation id, delete immediately (draft-safe)
        if (row?.violationRecordId) {
            this._beginWork();
            try {
                await deleteViolationAndFiles({ violationId: row.violationRecordId });
            } catch (e) {
                this.toast('Delete failed', this.reduceError(e), 'error');
                this._endWork();
                return;
            }
            this._endWork();
        }

        this.rows = (this.rows || []).filter(r => r.rowId !== rowId);
    }

    // ---------------------------
    // Upload finished (existing behavior)
    // ---------------------------
    async handleUploadFinished(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const uploadedFiles = event.detail.files || [];

        if (uploadedFiles.length > 1) {
            this.toast('Only 1 file allowed', 'Please upload only one file for this violation.', 'error');
            return;
        }

        const file = uploadedFiles[0];
        if (!file) return;

        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row) return;

        this.rows = (this.rows || []).map(r => {
            if (r.rowId !== rowId) return r;
            return {
                ...r,
                files: [{ name: file.name, documentId: file.documentId }],
                filesSummary: file.name,
                fileUrl: `/lightning/r/ContentDocument/${file.documentId}/view`,
                fileThumbUrl: null,
                fileError: false,
                fileErrorMessage: ''
            };
        });

        if (!row.documentId) {
            this.toast('Upload failed', 'Missing document placeholder. Please refresh and try again.', 'error');
            return;
        }

        this._beginWork();
        try {
            await updateViolationDocumentAfterUpload({
                documentId: row.documentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });
        } catch (e) {
            this.toast('Document update failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }

        await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'evidence', rowId });
    }

    async handleAfterUploadFinished(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const uploadedFiles = event.detail.files || [];

        if (uploadedFiles.length > 1) {
            this.toast('Only 1 file allowed', 'Please upload only one after photo per violation.', 'error');
            return;
        }

        const file = uploadedFiles[0];
        if (!file) return;

        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row) return;

        this.rows = (this.rows || []).map(r => {
            if (r.rowId !== rowId) return r;

            const updated = {
                ...r,
                afterFiles: [{ name: file.name, documentId: file.documentId }],
                afterFilesSummary: file.name,
                afterFileUrl: `/lightning/r/ContentDocument/${file.documentId}/view`,
                afterThumbUrl: null,
                afterFileError: false,
                afterFileErrorMessage: ''
            };
            return this.deriveRowState(updated);
        });

        // Baseline carried-forward row: create child violation + docs on first AFTER upload
        if (row.isParentBaselineRow && !row.violationRecordId) {
            this._beginWork();
            try {
                const resp = await createCarriedForwardViolationAndAttachAfter({
                    caseId: this.recordId,
                    vsrId: this.vsrId,
                    sourceViolationId: row.sourceViolationRecordId,
                    heading: row.name,
                    category: row.category,
                    description: row.description,
                    beforeContentDocumentId: row.beforeContentDocumentId,
                    beforeFileName: row.beforeFileName,
                    afterFileName: file.name,
                    afterContentDocumentId: file.documentId
                });
                this.rows = (this.rows || []).map(r => {
                    if (r.rowId !== rowId) return r;
                    return this.deriveRowState({
                        ...r,
                        violationRecordId: resp?.violationId || null,
                        afterDocumentId: resp?.afterDocumentId || null,
                        beforeDocumentId: resp?.beforeDocumentId || r.beforeDocumentId || null,
                        isParentBaselineRow: false
                    });
                });
            } catch (e) {
                this.toast('After photo upload failed', this.reduceError(e), 'error');
            } finally {
                this._endWork();
            }
        } else if (row.afterDocumentId) {
            this._beginWork();
            try {
                await updateViolationDocumentAfterUpload({
                    documentId: row.afterDocumentId,
                    fileName: file.name,
                    contentDocumentId: file.documentId
                });
            } catch (e) {
                this.toast('After photo update failed', this.reduceError(e), 'error');
            } finally {
                this._endWork();
            }
        }

        await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'after', rowId });
    }

    async handleBeforeUploadFinished(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const uploadedFiles = event.detail.files || [];

        if (uploadedFiles.length > 1) {
            this.toast('Only 1 file allowed', 'Please upload only one before photo per violation.', 'error');
            return;
        }

        const file = uploadedFiles[0];
        if (!file) return;

        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row) return;

        this.rows = (this.rows || []).map(r => {
            if (r.rowId !== rowId) return r;

            const updated = {
                ...r,
                beforeFiles: [{ name: file.name, documentId: file.documentId }],
                beforeFilesSummary: file.name,
                beforeFileUrl: `/lightning/r/ContentDocument/${file.documentId}/view`,
                beforeThumbUrl: null,
                beforeUploadError: false,
                beforeUploadErrorMessage: ''
            };
            return this.deriveRowState(updated);
        });

        if (!row.beforeDocumentId) {
            this.toast('Before photo upload failed', 'Missing document placeholder. Please refresh and try again.', 'error');
            return;
        }

        this._beginWork();
        try {
            await updateViolationDocumentAfterUpload({
                documentId: row.beforeDocumentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });
        } catch (e) {
            this.toast('Before photo update failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }

        await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'before', rowId });
    }

    // ---------------------------
    // NEW: Clear/remove file (re-enable upload UI)
    // ---------------------------
    async handleClearVillaFile() {
        if (!this.villaDocumentId) return;
        this._beginWork();
        try {
            await clearDocumentFile({ documentId: this.villaDocumentId });
            this.villaFiles = [];
            this.villaFilesSummary = '';
            this.villaFileUrl = null;
            this.villaThumbUrl = null;
            this.villaFileError = false;
            this.villaFileErrorMessage = '';
        } catch (e) {
            this.toast('Remove failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
    }

    async handleClearEvidenceFile(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row?.documentId) return;

        this._beginWork();
        try {
            await clearDocumentFile({ documentId: row.documentId });
            this.rows = (this.rows || []).map(r => {
                if (r.rowId !== rowId) return r;
                return {
                    ...r,
                    files: [],
                    filesSummary: '',
                    fileUrl: null,
                    fileThumbUrl: null,
                    fileError: false,
                    fileErrorMessage: '',
                    isDocNotReady: false
                };
            });
        } catch (e) {
            this.toast('Remove failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
    }

    async handleClearBeforeFile(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row?.beforeDocumentId) return;
        if (row.isRowDisabled) return;

        this._beginWork();
        try {
            await clearDocumentFile({ documentId: row.beforeDocumentId });
            this.rows = (this.rows || []).map(r => {
                if (r.rowId !== rowId) return r;
                return this.deriveRowState({
                    ...r,
                    beforeFiles: [],
                    beforeFilesSummary: '',
                    beforeFileUrl: null,
                    beforeThumbUrl: null,
                    beforeUploadError: false,
                    beforeUploadErrorMessage: '',
                    isBeforeDocNotReady: false
                });
            });
        } catch (e) {
            this.toast('Remove failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
    }

    async handleClearAfterFile(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row?.afterDocumentId) return;
        if (row.isRowDisabled) return;

        this._beginWork();
        try {
            await clearDocumentFile({ documentId: row.afterDocumentId });
            this.rows = (this.rows || []).map(r => {
                if (r.rowId !== rowId) return r;
                return this.deriveRowState({
                    ...r,
                    afterFiles: [],
                    afterFilesSummary: '',
                    afterFileUrl: null,
                    afterThumbUrl: null,
                    afterFileError: false,
                    afterFileErrorMessage: '',
                    isAfterDocNotReady: false
                });
            });
        } catch (e) {
            this.toast('Remove failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
    }

    // ---------------------------
    // Villa upload (existing)
    // ---------------------------
    async handleVillaUploadFinished(event) {
        const uploadedFiles = event.detail.files || [];

        if (uploadedFiles.length > 1) {
            this.toast('Only 1 file allowed', 'Please upload only one villa picture.', 'error');
            return;
        }

        const file = uploadedFiles[0];
        if (!file) return;

        this.villaFiles = [{ name: file.name, documentId: file.documentId }];
        this.villaFilesSummary = file.name;
        this.villaFileUrl = `/lightning/r/ContentDocument/${file.documentId}/view`;
        this.villaThumbUrl = null;
        this.villaFileError = false;
        this.villaFileErrorMessage = '';

        try {
            const docId = await upsertVillaAndAttach({
                caseId: this.recordId,
                vsrId: this.vsrId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });
            this.villaDocumentId = docId;

            await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'villa' });

        } catch (e) {
            this.toast('Villa picture update failed', this.reduceError(e), 'error');
        }
    }

    // After placeholders are no longer created on load.
    // Baseline carried-forward rows create their child violation + docs on first AFTER upload.

    // ---------------------------
    // Fixed checkbox
    // ---------------------------
    handleRowIsFixedChange(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const checked = event.target.checked;

        this.rows = (this.rows || []).map(r => {
            if (r.rowId !== rowId) return r;
            if (r.isNewInFollowUp) return this.deriveRowState({ ...r, isFixed: false });
            if (r.isRowDisabled) return this.deriveRowState({ ...r });
            return this.deriveRowState({ ...r, isFixed: checked });
        });
    }

    // ---------------------------
    // Submit (unchanged from your provided code)
    // ---------------------------
    async handleSubmit() {
        if (this.isSubmitting) return;
        this.isSubmitting = true;

        try {
            const deletedViolationIdsJson = JSON.stringify([]);
            const deletedDocumentIdsJson = JSON.stringify([]);

            const invalidOther = (this.rows || []).some(r =>
                r.isOther && (!r.category || !r.category.trim() || !r.description || !r.description.trim())
            );
            if (invalidOther) {
                this.toast('Missing details', 'For "Other", please select a category and enter a description.', 'error');
                return;
            }

            const status = this.computeStatusFromRows(this.rows);

            const requireVilla = !(this.rows && this.rows.length) || status === 'Clear' || status === 'Clear with Comments';
            if (requireVilla) {
                const hasVilla = this.villaFiles && this.villaFiles.length;
                if (!hasVilla) {
                    this.villaFileError = true;
                    this.villaFileErrorMessage = 'Please upload the villa picture (required for Clear / Clear with Comments, or when there are no violations).';
                    this.toast('Missing villa picture', this.villaFileErrorMessage, 'error');
                    return;
                }
            }

            if (this.isParentMode) {
                const missingAfter = (this.rows || []).filter(r =>
                    r.isCarriedForward && !r.isFixedOnLoad && !(r.afterFiles && r.afterFiles.length)
                );

                const missingBefore = (this.rows || []).filter(r =>
                    r.isNewInFollowUp && !(r.beforeFiles && r.beforeFiles.length)
                );

                if (missingAfter.length || missingBefore.length) {
                    this.rows = (this.rows || []).map(r =>
                        this.deriveRowState({
                            ...r,
                            afterFileError: r.isCarriedForward && !r.isFixedOnLoad && !(r.afterFiles && r.afterFiles.length),
                            afterFileErrorMessage: (r.isCarriedForward && !r.isFixedOnLoad && !(r.afterFiles && r.afterFiles.length))
                                ? 'Please upload an after photo for this carried-forward violation.'
                                : '',
                            beforeUploadError: r.isNewInFollowUp && !(r.beforeFiles && r.beforeFiles.length),
                            beforeUploadErrorMessage: (r.isNewInFollowUp && !(r.beforeFiles && r.beforeFiles.length))
                                ? 'Please upload a before photo for this new violation.'
                                : ''
                        })
                    );

                    this.toast('Missing photos', 'Upload required photos before submitting.', 'error');
                    return;
                }
            } else {
                const missingFileRows = (this.rows || []).filter(r => !(r.files && r.files.length));
                if (missingFileRows.length) {
                    this.rows = (this.rows || []).map(r => {
                        const hasFile = r.files && r.files.length;
                        return {
                            ...r,
                            fileError: !hasFile,
                            fileErrorMessage: !hasFile ? 'Please upload a document for this violation.' : ''
                        };
                    });
                    this.toast('Missing documents', 'Please upload a document for every violation before submitting.', 'error');
                    return;
                }
            }

            let payloadRows;
            if (this.isParentMode) {
                payloadRows = (this.rows || []).map(r => ({
                    violationRecordId: r.violationRecordId || null,
                    sourceViolationRecordId: r.sourceViolationRecordId || null,
                    name: r.name,
                    category: r.category,
                    description: r.description,
                    isCarriedForward: !!r.isCarriedForward,
                    isFixedOnLoad: !!r.isFixedOnLoad,
                    isFixed: r.isNewInFollowUp ? false : !!r.isFixed,
                    beforeDocumentId: r.isNewInFollowUp ? (r.beforeDocumentId || null) : null,
                    afterDocumentId: r.isCarriedForward ? (r.afterDocumentId || null) : null,
                    beforeContentDocumentId: r.beforeContentDocumentId || null,
                    beforeFileName: r.beforeFileName || null
                }));
            } else {
                payloadRows = (this.rows || []).map(r => ({
                    violationRecordId: r.violationRecordId || null,
                    documentId: r.documentId || null,
                    name: r.name,
                    category: r.category,
                    description: r.description,
                    isFixed: !!r.isFixed
                }));
            }

            const savedVsrId = await submitVSR({
                caseId: this.recordId,
                rowsJson: JSON.stringify(payloadRows),
                villaDocumentId: this.villaDocumentId,
                deletedViolationIdsJson,
                deletedDocumentIdsJson
            });

            const title = this._vsrWasDraftOnOpen ? 'Submitted' : 'Updated';
            const msg = this._vsrWasDraftOnOpen
                ? `VSR submitted successfully (${savedVsrId}).`
                : `VSR updated successfully (${savedVsrId}).`;
            this.toast(title, msg, 'success');
            this._vsrWasDraftOnOpen = false;

            // Keep spinner briefly so the user can perceive success before the modal closes/reloads.
            await this.sleep(1200);

            this.dispatchEvent(new CustomEvent('submitted', { detail: { vsrId: savedVsrId } }));
            setTimeout(() => this.refreshRecordPage(), 0);

        } catch (error) {
            this.toast('Submit failed', this.reduceError(error), 'error');
        } finally {
            this.isSubmitting = false;
        }
    }

    sleep(ms) {
        return new Promise(resolve => window.setTimeout(resolve, ms));
    }

    computeStatusFromRows(rows) {
        const r = rows || [];
        if (!r.length) return 'Clear';

        if (!this.isParentMode) {
            let anyRY = false;
            let anyOrange = false;

            r.forEach(x => {
                const cat = (x.category || '').trim().toLowerCase();
                if (cat === 'red' || cat === 'yellow') anyRY = true;
                if (cat === 'orange') anyOrange = true;
            });

            if (anyRY) return 'Non-Compliant';
            if (anyOrange) return 'Clear with Comments';
            return 'Clear';
        }

        let cf_anyRY = false, cf_anyOrange = false, cf_ryNotFixed = false, cf_orangeNotFixed = false;
        let new_anyRY = false, new_anyOrange = false;

        r.forEach(x => {
            const cat = (x.category || '').trim().toLowerCase();
            const fixed = !!x.isFixed;

            if (x.isCarriedForward) {
                if (cat === 'red' || cat === 'yellow') {
                    cf_anyRY = true;
                    if (!fixed) cf_ryNotFixed = true;
                }
                if (cat === 'orange') {
                    cf_anyOrange = true;
                    if (!fixed) cf_orangeNotFixed = true;
                }
            } else {
                if (cat === 'red' || cat === 'yellow') new_anyRY = true;
                if (cat === 'orange') new_anyOrange = true;
            }
        });

        let status;
        if (!cf_anyRY && !cf_anyOrange) status = 'Clear';
        else if (!cf_anyRY && cf_anyOrange) status = cf_orangeNotFixed ? 'Clear with Comments' : 'Clear';
        else status = cf_ryNotFixed ? 'Non-Compliant' : (cf_orangeNotFixed ? 'Clear with Comments' : 'Clear');

        if (status !== 'Non-Compliant') {
            if (new_anyRY) status = 'Non-Compliant';
            else if (new_anyOrange) status = 'Clear with Comments';
        }

        return status;
    }

    // ---------------------------
    // Row State / Disable Rules
    // ---------------------------
    deriveRowState(row) {
        const isParentMode = !!this.isParentMode;

        const isCarriedForward = !!row.isCarriedForward;
        const isNewInFollowUp = isParentMode && !isCarriedForward;

        const isRowDisabled = isParentMode && !!row.isFixedOnLoad;

        // Parent baseline rows (sourced from parent case) are read-only except AFTER upload.
        const isParentBaselineRow = isParentMode
            && !!row.isCarriedForward
            && !!row.sourceViolationRecordId
            && !row.violationRecordId;

        // Carried-forward (copied) rows: lock Category + Delete in follow-up cases
        const isCarriedForwardLocked = isParentMode && isCarriedForward;

        const isCategoryDisabledFinal = !!row.isCategoryDisabled || isRowDisabled || isParentBaselineRow || isCarriedForwardLocked;
        const isDescriptionDisabled = (!!row.isOther && isRowDisabled) || (row.isOther && isParentBaselineRow);

        const isBeforeUploadDisabled = isRowDisabled || !!row.isBeforeDocNotReady || isParentBaselineRow;
        const isAfterUploadDisabled = isParentBaselineRow ? false : (isRowDisabled || !!row.isAfterDocNotReady);

        // Parent baseline rows: allow Fixed checkbox + After upload (everything else read-only)
        const isFixedDisabledFinal = isNewInFollowUp || isRowDisabled;
        const isFixed = isNewInFollowUp ? false : !!row.isFixed;

        const isDeleteDisabledFinal = isRowDisabled || isParentBaselineRow || isCarriedForwardLocked;

        const beforeUploadRecordId = row.beforeDocumentId || this.vsrId;
        const afterUploadRecordId = row.afterDocumentId || this.vsrId;

        const isAfterRemoveDisabled = isRowDisabled || isParentBaselineRow || isCarriedForwardLocked;

        return {
            ...row,
            isCarriedForward,
            isNewInFollowUp,
            isRowDisabled,
            isParentBaselineRow,
            isCarriedForwardLocked,
            isCategoryDisabledFinal,
            isDescriptionDisabled,
            isBeforeUploadDisabled,
            isAfterUploadDisabled,
            isFixedDisabledFinal,
            isDeleteDisabledFinal,
            isFixed,
            beforeUploadRecordId,
            afterUploadRecordId,
            isAfterRemoveDisabled
        };
    }

    // ---------------------------
    // UI helpers
    // ---------------------------
    get categoryOptions() {
        return [
            { label: 'Red', value: 'Red' },
            { label: 'Yellow', value: 'Yellow' },
            { label: 'Orange', value: 'Orange' }
        ];
    }

    handleRowCategoryChange(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const newCategory = event.detail.value;

        this.rows = (this.rows || []).map(r => r.rowId !== rowId ? r : this.deriveRowState({
            ...r,
            category: newCategory
        }));
    }

    handleRowDescriptionChange(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const newDesc = event.target.value;

        this.rows = (this.rows || []).map(r => r.rowId !== rowId ? r : this.deriveRowState({
            ...r,
            description: newDesc
        }));
    }

    generateRowId() {
        return `ROW-${Date.now()}-${Math.random().toString(16).slice(2)}`;
    }

    toast(title, message, variant) {
        this.dispatchEvent(new ShowToastEvent({ title, message, variant }));
    }

    // If thumbnail URL fails to load, hide the image to avoid broken/crash icon.
    handleThumbError = (event) => {
        const scope = event?.target?.dataset?.scope;
        const rowId = event?.target?.dataset?.rowid;

        if (scope === 'villa') {
            this.villaThumbUrl = null;
            return;
        }

        if (!rowId) return;
        this.rows = (this.rows || []).map(r => {
            if (r.rowId !== rowId) return r;
            if (scope === 'before') return { ...r, beforeThumbUrl: null };
            if (scope === 'after') return { ...r, afterThumbUrl: null };
            if (scope === 'evidence') return { ...r, fileThumbUrl: null };
            return r;
        });
    };

    reduceError(error) {
        try {
            if (!error) return 'Unknown error';
            if (error.body) {
                if (typeof error.body.message === 'string') return error.body.message;
                if (Array.isArray(error.body)) return error.body.map(e => e.message).join(', ');
                if (Array.isArray(error.body.pageErrors) && error.body.pageErrors.length) {
                    return error.body.pageErrors.map(e => e.message).join(', ');
                }
            }
            if (typeof error.message === 'string') return error.message;
            return JSON.stringify(error);
        } catch (e) {
            return 'Unknown error';
        }
    }

    refreshRecordPage() {
        try {
            notifyRecordUpdateAvailable([{ recordId: this.recordId }]);
        } catch (e) {
            // noop
        }
    }

    // ---------------------------
    // Thumbnails
    // ---------------------------
    async hydrateThumbnailsForContentDocs(contentDocumentIds, { scope, rowId } = {}) {
        const ids = (contentDocumentIds || []).filter(Boolean);
        if (!ids.length) return;

        try {
            const map = await getThumbnailUrls({ contentDocumentIds: ids });
            if (!map) return;

            if (scope === 'villa') {
                const first = ids[0];
                this.villaThumbUrl = map[first] || null;
                return;
            }

            this.rows = (this.rows || []).map(r => {
                if (rowId && r.rowId !== rowId) return r;

                if (scope === 'before') {
                    const cdId = r.beforeFiles?.[0]?.documentId;
                    return { ...r, beforeThumbUrl: cdId ? (map[cdId] || r.beforeThumbUrl) : r.beforeThumbUrl };
                }

                if (scope === 'after') {
                    const cdId = r.afterFiles?.[0]?.documentId;
                    return { ...r, afterThumbUrl: cdId ? (map[cdId] || r.afterThumbUrl) : r.afterThumbUrl };
                }

                if (scope === 'evidence') {
                    const cdId = r.files?.[0]?.documentId;
                    return { ...r, fileThumbUrl: cdId ? (map[cdId] || r.fileThumbUrl) : r.fileThumbUrl };
                }

                return r;
            });
        } catch (e) {
            // best-effort
        }
    }

    async hydrateAllThumbnails() {
        const villaCd = this.villaFiles?.[0]?.documentId;
        if (villaCd) await this.hydrateThumbnailsForContentDocs([villaCd], { scope: 'villa' });

        const beforeIds = (this.rows || []).map(r => r.beforeFiles?.[0]?.documentId).filter(Boolean);
        const afterIds = (this.rows || []).map(r => r.afterFiles?.[0]?.documentId).filter(Boolean);
        const evidenceIds = (this.rows || []).map(r => r.files?.[0]?.documentId).filter(Boolean);

        if (beforeIds.length) await this.hydrateThumbnailsForContentDocs(beforeIds, { scope: 'before' });
        if (afterIds.length) await this.hydrateThumbnailsForContentDocs(afterIds, { scope: 'after' });
        if (evidenceIds.length) await this.hydrateThumbnailsForContentDocs(evidenceIds, { scope: 'evidence' });
    }
}

