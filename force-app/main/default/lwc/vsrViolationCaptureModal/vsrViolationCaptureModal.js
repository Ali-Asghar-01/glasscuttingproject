import LightningModal from 'lightning/modal';
import { api } from 'lwc';

export default class VsrViolationCaptureModal extends LightningModal {
    @api recordId;
    isClosing = false;
    _isClosing = false;

    handleHeaderClose() {
        this.closeAndHardRefresh({ action: 'close' });
    }

    handleClose() {
        this.closeAndHardRefresh({ action: 'close' });
    }

    handleSubmitted(event) {
        // Close immediately on success; reload shortly after so the success toast is visible.
        this.closeAndHardRefresh({ action: 'submitted', vsrId: event.detail?.vsrId }, { delayBeforeCloseMs: 0, delayBeforeReloadMs: 1200 });
    }

    async handleSubmitClick() {
        const cmp = this.template.querySelector('c-v-s-r-violation-capture-component');
        if (cmp && typeof cmp.submitFromHost === 'function') {
            await cmp.submitFromHost();
        }
    }

    closeAndHardRefresh(payload, { delayBeforeCloseMs = 1000, delayBeforeReloadMs = 0 } = {}) {
        if (this._isClosing) return;
        this._isClosing = true;
        this.isClosing = true;

        window.setTimeout(() => {
            try {
                this.close(payload);
            } finally {
                window.setTimeout(() => window.location.reload(), delayBeforeReloadMs);
            }
        }, delayBeforeCloseMs);
    }
}

