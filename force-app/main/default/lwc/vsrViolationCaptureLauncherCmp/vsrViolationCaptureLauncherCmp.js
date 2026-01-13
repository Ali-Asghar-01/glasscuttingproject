import { LightningElement, api } from 'lwc';
import VsrViolationCaptureModal from 'c/vsrViolationCaptureModal';
import { ShowToastEvent } from 'lightning/platformShowToastEvent';

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
            const result = await VsrViolationCaptureModal.open({
                size: 'large',
                description: 'Violation Capture',
                recordId: this.recordId
            });

            if (result?.action === 'submitted') {
                this.dispatchEvent(
                    new ShowToastEvent({
                        title: 'Submitted',
                        message: `VSR submitted (${result.vsrId}).`,
                        variant: 'success'
                    })
                );
            }
        } catch (e) {
            // user closed modal or unexpected error
        }
    }
}

