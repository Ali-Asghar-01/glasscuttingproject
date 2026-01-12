import { LightningElement, api, wire } from 'lwc';
import { getRecord } from 'lightning/uiRecordApi';

const FIELDS = ['Case.Sent_Offer_Type__c', 'Case.Type'];

export default class PreferredAppointmentTimeCard extends LightningElement {
    @api recordId;

    _case;
    _error;

    @wire(getRecord, { recordId: '$recordId', fields: FIELDS })
    wiredCase({ data, error }) {
        this._case = data;
        this._error = error;
    }

    get inspectionMode() {
        return this._getFieldValue('Sent_Offer_Type__c');
    }

    get preferredSlot() {
        return this._getFieldValue('Type');
    }

    _getFieldValue(fieldApiName) {
        try {
            const v = this._case?.fields?.[fieldApiName]?.value;
            return v && String(v).trim() ? v : '—';
        } catch (e) {
            return '—';
        }
    }
}

