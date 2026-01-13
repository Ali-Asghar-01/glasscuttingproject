import { LightningElement, api } from 'lwc';
import VsrViolationCaptureModal from 'c/vsrViolationCaptureModal';
import { ShowToastEvent } from 'lightning/platformShowToastEvent';
import initVsrForModal from '@salesforce/apex/vSRViolationCaptureController.initVsrForModal';

export default class VsrViolationCaptureLauncherCmp extends LightningElement {
    @api recordId; // Salesforce injects this when used on a record page action

    @api async invoke() {
        if (!this.recordId) {
            this.dispatchEvent(
                new ShowToastEvent({
                    title: 'Missing Case Id',
                    message: 'This action must be opened from a Case record page.',
                    variant: 'error'
                })
            );
            return;
        }

        try {
            // Create/reuse VSR as soon as the action is invoked (modal open).
            const vsrId = await initVsrForModal({ caseId: this.recordId });
            await VsrViolationCaptureModal.open({
                size: 'large',
                description: 'Violation Capture',
                recordId: this.recordId,
                vsrId
            });
        } catch (e) {
            // user closed modal or unexpected error
        }
    }
}

