import LightningModal from 'lightning/modal';
import { api } from 'lwc';

export default class VsrViolationCaptureModal extends LightningModal {
    @api recordId;

    handleHeaderClose() {
        this._closeAndHardRefresh({ action: 'close' });
    }

    handleClose() {
        this._closeAndHardRefresh({ action: 'close' });
    }

    handleSubmitted(event) {
        this._closeAndHardRefresh({ action: 'submitted', vsrId: event.detail?.vsrId });
    }

    async handleSubmitClick() {
        const cmp = this.template.querySelector('c-v-s-r-violation-capture-component');
        if (cmp && typeof cmp.submitFromHost === 'function') {
            await cmp.submitFromHost();
        }
    }

    _closeAndHardRefresh(payload) {
        try {
            this.close(payload);
        } finally {
            // Hard refresh after close/submit/X (defer so modal can close cleanly)
            window.setTimeout(() => {
                try {
                    window.location.reload();
                } catch (e) {
                    // ignore
                }
            }, 0);
        }
    }
}

