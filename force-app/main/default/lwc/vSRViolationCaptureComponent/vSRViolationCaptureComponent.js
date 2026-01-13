import { LightningElement, api, track } from 'lwc';
import { ShowToastEvent } from 'lightning/platformShowToastEvent';
import { NavigationMixin } from 'lightning/navigation';
import { notifyRecordUpdateAvailable } from 'lightning/uiRecordApi';

import getVSRViolationRecords from '@salesforce/apex/vSRViolationCaptureController.getVSRViolationRecords';
import getVsrContext from '@salesforce/apex/vSRViolationCaptureController.getVsrContext';
import initVsrForModal from '@salesforce/apex/vSRViolationCaptureController.initVsrForModal';
import submitVSR from '@salesforce/apex/vSRViolationCaptureController.submitVSR';

import createViolationForVsr from '@salesforce/apex/vSRViolationCaptureController.createViolationForVsr';
import updateViolationDocumentAfterUpload from '@salesforce/apex/vSRViolationCaptureController.updateViolationDocumentAfterUpload';

import createVillaPictureDocument from '@salesforce/apex/vSRViolationCaptureController.createVillaPictureDocument';
import updateVillaPictureAfterUpload from '@salesforce/apex/vSRViolationCaptureController.updateVillaPictureAfterUpload';

import getOrCreateAfterPhotoDocuments from '@salesforce/apex/vSRViolationCaptureController.getOrCreateAfterPhotoDocuments';
import getThumbnailUrls from '@salesforce/apex/vSRViolationCaptureController.getThumbnailUrls';

// NEW: remove/clear uploaded file (re-enables upload UI)
import clearDocumentFile from '@salesforce/apex/vSRViolationCaptureController.clearDocumentFile';

export default class VSRViolationCaptureComponent extends NavigationMixin(LightningElement) {
    @api recordId;
    @api vsrId; // passed from launcher/modal (created on action invoke)

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

    // Pending deletes (rows)
    pendingDeleteViolationIds = new Set();
    pendingDeleteDocumentIds = new Set();

    // Upload formats
    acceptedFormats = ['.png', '.jpg', '.jpeg', '.mp4', '.mov'];

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
            // Safety: if launcher didn't pass vsrId for any reason, create/reuse it here.
            if (!this.vsrId) {
                this.vsrId = await initVsrForModal({ caseId: this.recordId });
            }
            await this.loadViolations();
            await this.loadExistingVsrRows();
            await this.initVillaPicture();
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
            const jsonStr = await getVsrContext({ caseId: this.recordId, vsrId: this.vsrId });
            const data = JSON.parse(jsonStr || '{}');

            this.isParentMode = !!data.isFollowUp;
            this.isBaselineMode = !!data.sourceIsParentBaseline;

            // Villa doc (current case)
            this.villaDocumentId = data.villaDocumentId || null;
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
                // Should not happen after initVsrForModal, but keep defensive behavior.
                this.rows = [];
                this.applyViolationFilter();
                return;
            }

            // IMPORTANT: controller returns current-case VSR Id (the one user is editing)
            this.vsrId = data.vsrId || this.vsrId;

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
                        isAfterDocNotReady: !afterDocId,
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
                    files: contentDocumentId ? [{ name: fileName || 'Uploaded file', documentId: contentDocumentId }] : [],
                    filesSummary: contentDocumentId ? (fileName || 'Uploaded file') : '',
                    fileUrl: contentDocumentId ? `/lightning/r/ContentDocument/${contentDocumentId}/view` : null,
                    fileThumbUrl: null,
                    fileError: false,
                    fileErrorMessage: '',
                    isDocNotReady: !r.documentId,
                    isDocCreating: false
                };
            });

            this.rows = loadedRows;

            if (this.isParentMode && this.rows.length) {
                await this.ensureAfterDocsForRows();
                this.rows = (this.rows || []).map(x => this.deriveRowState(x));
            }

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

        if (this.isParentMode) {
            const row = this.deriveRowState({
                rowId,
                violationRecordId: null,
                sourceViolationRecordId: null,

                name: violation.name,
                category: isOther ? '' : violation.category,
                description: isOther ? '' : violation.description,
                isOther,

                isCarriedForward: false,
                isFixedOnLoad: false,
                isFixed: false,

                beforeDocumentId: null,
                beforeContentDocumentId: null,
                beforeFileName: null,
                beforeFiles: [],
                beforeFilesSummary: '',
                beforeFileUrl: null,
                beforeThumbUrl: null,

                afterDocumentId: null,
                afterFiles: [],
                afterFilesSummary: '',
                afterFileUrl: null,
                afterThumbUrl: null,

                isCategoryDisabled: false,
                isBeforeDocNotReady: true,
                isBeforeDocCreating: true,
                isAfterDocNotReady: true,
                isAfterDocCreating: false,

                beforeUploadError: false,
                beforeUploadErrorMessage: '',
                afterFileError: false,
                afterFileErrorMessage: ''
            });

            this.rows = [...this.rows, row];

            this.isAddInProgress = true;
            this._beginWork();
            try {
                const resp = await createViolationForVsr({
                    caseId: this.recordId,
                    vsrId: this.vsrId,
                    heading: violation.name,
                    category: isOther ? '' : violation.category,
                    description: isOther ? '' : violation.description,
                    isParentMode: true
                });
                const violationId = resp?.violationId || resp?.violationid || null;
                const documentId = resp?.documentId || resp?.documentid || null;

                this.rows = (this.rows || []).map(r => {
                    if (r.rowId !== rowId) return r;
                    const updated = {
                        ...r,
                        violationRecordId: violationId,
                        beforeDocumentId: documentId,
                        isBeforeDocNotReady: !documentId,
                        isBeforeDocCreating: false
                    };
                    return this.deriveRowState(updated);
                });

            } catch (e) {
                this.rows = (this.rows || []).filter(r => r.rowId !== rowId);
                this.dispatchEvent(new ShowToastEvent({
                    title: 'Document creation failed',
                    message: this.reduceError(e),
                    variant: 'error'
                }));
            } finally {
                this._endWork();
                this.isAddInProgress = false;
            }

        } else {
            const row = {
                rowId,
                violationRecordId: null,

                documentId: null,
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

                isDocNotReady: true,
                isDocCreating: true,

                isFixed: false,
                isCategoryDisabled: false
            };

            this.rows = [...this.rows, row];

            this.isAddInProgress = true;
            this._beginWork();
            try {
                const resp = await createViolationForVsr({
                    caseId: this.recordId,
                    vsrId: this.vsrId,
                    heading: violation.name,
                    category: isOther ? '' : violation.category,
                    description: isOther ? '' : violation.description,
                    isParentMode: false
                });
                const violationId = resp?.violationId || resp?.violationid || null;
                const documentId = resp?.documentId || resp?.documentid || null;

                this.rows = (this.rows || []).map(r => {
                    if (r.rowId !== rowId) return r;
                    return {
                        ...r,
                        violationRecordId: violationId,
                        documentId,
                        isDocNotReady: !documentId,
                        isDocCreating: false
                    };
                });

            } catch (e) {
                this.rows = (this.rows || []).filter(r => r.rowId !== rowId);
                this.dispatchEvent(new ShowToastEvent({
                    title: 'Document creation failed',
                    message: this.reduceError(e),
                    variant: 'error'
                }));
            } finally {
                this._endWork();
                this.isAddInProgress = false;
            }
        }
    }

    // ---------------------------
    // Deletes (rows)
    // ---------------------------
    handleDeleteRow(event) {
        const rowId = event.currentTarget.dataset.rowid;
        const row = (this.rows || []).find(r => r.rowId === rowId);
        if (!row) return;

        if (this.isParentMode && row.isRowDisabled) return;

        if (this.isParentMode) {
            if (row?.violationRecordId) this.pendingDeleteViolationIds.add(row.violationRecordId);
            if (row?.afterDocumentId) this.pendingDeleteDocumentIds.add(row.afterDocumentId);
            // IMPORTANT: never delete parent-case "Before" docs from a carried-forward baseline row.
            // Only delete the BEFORE placeholder when the row is new-in-follow-up (current case).
            if (row?.isNewInFollowUp && row?.beforeDocumentId) {
                this.pendingDeleteDocumentIds.add(row.beforeDocumentId);
            }
        } else {
            if (row?.violationRecordId) this.pendingDeleteViolationIds.add(row.violationRecordId);
            if (row?.documentId) this.pendingDeleteDocumentIds.add(row.documentId);
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
        if (!row?.documentId) return;

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

        this._beginWork();
        try {
            await updateViolationDocumentAfterUpload({
                documentId: row.documentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });

            await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'evidence', rowId });

        } catch (e) {
            this.toast('Document update failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
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
        if (!row?.afterDocumentId) return;

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

        this._beginWork();
        try {
            await updateViolationDocumentAfterUpload({
                documentId: row.afterDocumentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });

            await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'after', rowId });

        } catch (e) {
            this.toast('After photo update failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
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
        if (!row?.beforeDocumentId) return;

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

        this._beginWork();
        try {
            await updateViolationDocumentAfterUpload({
                documentId: row.beforeDocumentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });

            await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'before', rowId });

        } catch (e) {
            this.toast('Before photo update failed', this.reduceError(e), 'error');
        } finally {
            this._endWork();
        }
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
    async initVillaPicture() {
        if (this.villaDocumentId) return;
        try {
            this.villaDocumentId = await createVillaPictureDocument({ caseId: this.recordId });
        } catch (e) {
            this.toast('Villa picture document creation failed', this.reduceError(e), 'error');
        }
    }

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
            await updateVillaPictureAfterUpload({
                documentId: this.villaDocumentId,
                fileName: file.name,
                contentDocumentId: file.documentId
            });

            await this.hydrateThumbnailsForContentDocs([file.documentId], { scope: 'villa' });

        } catch (e) {
            this.toast('Villa picture update failed', this.reduceError(e), 'error');
        }
    }

    // ---------------------------
    // After placeholders (carried-forward only)
    // ---------------------------
    async ensureAfterDocsForRows() {
        const targets = (this.rows || []).filter(r => r.isCarriedForward && !r.afterDocumentId);

        const idsNeedingDocs = targets
            .map(r => r.sourceViolationRecordId || r.violationRecordId)
            .filter(Boolean);

        if (!idsNeedingDocs.length) return;

        this.rows = (this.rows || []).map(r => {
            if (!r.isCarriedForward || r.afterDocumentId) return r;
            return { ...r, isAfterDocCreating: true };
        });

        let mapResp;
        this._beginWork();
        try {
            mapResp = await getOrCreateAfterPhotoDocuments({
                caseId: this.recordId,
                sourceViolationIds: idsNeedingDocs
            });
        } catch (e) {
            this.toast('After photo placeholders failed', this.reduceError(e), 'error');
            return;
        } finally {
            this._endWork();
        }

        this.rows = (this.rows || []).map(r => {
            if (!r.isCarriedForward || r.afterDocumentId) return { ...r, isAfterDocCreating: false };

            const key = r.sourceViolationRecordId || r.violationRecordId;
            const docId = mapResp ? mapResp[key] : null;

            return this.deriveRowState({
                ...r,
                afterDocumentId: docId || null,
                isAfterDocNotReady: !docId,
                isAfterDocCreating: false
            });
        });
    }

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
            const deletedViolationIdsJson = JSON.stringify(Array.from(this.pendingDeleteViolationIds));
            const deletedDocumentIdsJson = JSON.stringify(Array.from(this.pendingDeleteDocumentIds));

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
                vsrId: this.vsrId,
                rowsJson: JSON.stringify(payloadRows),
                villaDocumentId: this.villaDocumentId,
                deletedViolationIdsJson,
                deletedDocumentIdsJson
            });

            this.toast('Submitted', `VSR saved successfully (${savedVsrId}).`, 'success');
            this.dispatchEvent(new CustomEvent('submitted', { detail: { vsrId: savedVsrId } }));
            setTimeout(() => this.refreshRecordPage(), 0);

        } catch (error) {
            this.toast('Submit failed', this.reduceError(error), 'error');
        } finally {
            this.isSubmitting = false;
        }
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

        const isCategoryDisabledFinal = !!row.isCategoryDisabled || isRowDisabled;
        const isDescriptionDisabled = !!row.isOther && isRowDisabled;

        const isBeforeUploadDisabled = isRowDisabled || !!row.isBeforeDocNotReady;
        const isAfterUploadDisabled = isRowDisabled || !!row.isAfterDocNotReady;

        const isFixedDisabledFinal = isNewInFollowUp || isRowDisabled;
        const isFixed = isNewInFollowUp ? false : !!row.isFixed;

        return {
            ...row,
            isCarriedForward,
            isNewInFollowUp,
            isRowDisabled,
            isCategoryDisabledFinal,
            isDescriptionDisabled,
            isBeforeUploadDisabled,
            isAfterUploadDisabled,
            isFixedDisabledFinal,
            isFixed
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

