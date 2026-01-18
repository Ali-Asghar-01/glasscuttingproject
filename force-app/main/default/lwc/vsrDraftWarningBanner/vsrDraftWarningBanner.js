import { LightningElement, api, wire } from 'lwc';
import hasDraftVsr from '@salesforce/apex/VsrDraftStatusController.hasDraftVsr';

export default class VsrDraftWarningBanner extends LightningElement {
    @api recordId;
    showBanner = false;

    @wire(hasDraftVsr, { caseId: '$recordId' })
    wiredHasDraft({ data, error }) {
        if (error) {
            this.showBanner = false;
            return;
        }
        this.showBanner = !!data;
    }
}

