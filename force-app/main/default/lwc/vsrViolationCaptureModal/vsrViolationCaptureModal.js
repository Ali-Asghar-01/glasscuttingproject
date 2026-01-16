import LightningModal from 'lightning/modal';
import { api } from 'lwc';

export default class VsrViolationCaptureModal extends LightningModal {
    @api recordId;
    _isClosing = false;

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
        if (this._isClosing) return;
        this._isClosing = true;

        // Give the user a brief moment to perceive the final spinner/toast,
        // then close and hard-refresh.
        window.setTimeout(() => {
            try {
                this.close(payload);
            } finally {
                window.setTimeout(() => window.location.reload(), 0);
            }
        }, 1000);
    }
}

