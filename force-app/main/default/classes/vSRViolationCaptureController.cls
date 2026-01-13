public with sharing class vSRViolationCaptureController {
    // NOTE: This file only includes the new method needed by the updated UI behavior.
    // Merge this method into your existing controller in your org/repo.

    /**
     * Clears the file currently associated to a Documents__c placeholder and hard-deletes the file.
     * Used by the LWC "X" remove button to re-enable the upload UI.
     */
    @AuraEnabled
    public static void clearDocumentFile(Id documentId) {
        if (documentId == null) {
            throw new AuraHandledException('Document Id is required.');
        }

        Documents__c doc = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Id = :documentId
            LIMIT 1
        ];

        Id oldCdId = null;
        if (!String.isBlank(doc.AttachmentID__c)) {
            try { oldCdId = (Id) doc.AttachmentID__c; } catch (Exception ignore) {}
        }

        // Clear reference first (so validation checks read as "no file")
        if (!String.isBlank(doc.AttachmentID__c)) {
            doc.AttachmentID__c = null;
            update doc;
        }

        // Delete file everywhere it is linked (consistent with existing "replace" behavior)
        if (oldCdId != null) {
            safeDeleteFileHard(oldCdId);
        }
    }

    // Copied helper from your existing controller (keep in sync)
    private static void safeDeleteFileHard(Id contentDocumentId) {
        if (contentDocumentId == null) return;

        try {
            List<ContentDocumentLink> links = [
                SELECT Id
                FROM ContentDocumentLink
                WHERE ContentDocumentId = :contentDocumentId
            ];
            if (!links.isEmpty()) Database.delete(links, false);
        } catch (Exception e) {
            System.debug('safeDeleteFileHard: link delete failed: ' + e.getMessage());
        }

        try {
            List<ContentDocument> docs = [
                SELECT Id
                FROM ContentDocument
                WHERE Id = :contentDocumentId
                LIMIT 1
            ];
            if (!docs.isEmpty()) Database.delete(docs, false);
        } catch (Exception e) {
            System.debug('safeDeleteFileHard: content doc delete failed: ' + e.getMessage());
        }
    }
}

