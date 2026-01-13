import LightningModal from 'lightning/modal';
import { api } from 'lwc';

export default class VsrViolationCaptureModal extends LightningModal {
    @api recordId;

    handleHeaderClose() {
        this.closeAndHardRefresh({ action: 'close' });
    }

    handleClose() {
        this.closeAndHardRefresh({ action: 'close' });
    }

    handleSubmitted(event) {
        this.closeAndHardRefresh({ action: 'submitted', vsrId: event.detail?.vsrId });
    }

    async handleSubmitClick() {
        const cmp = this.template.querySelector('c-v-s-r-violation-capture-component');
        if (cmp && typeof cmp.submitFromHost === 'function') {
            await cmp.submitFromHost();
        }
    }

    closeAndHardRefresh(payload) {
        try {
            this.close(payload);
        } finally {
            // defer so the modal closes cleanly first
            window.setTimeout(() => window.location.reload(), 0);
        }
    }
}

