public with sharing class VsrDraftStatusController {
    @AuraEnabled(cacheable=true)
    public static Boolean hasDraftVsr(Id caseId) {
        if (caseId == null) return false;

        List<VSR__c> vsrs = [
            SELECT Id, Status__c
            FROM VSR__c
            WHERE Case__c = :caseId
            LIMIT 2
        ];
        if (vsrs.size() > 1) {
            // per overall solution constraint: one VSR per Case
            throw new AuraHandledException('More than one VSR exists for this Case. Please contact support.');
        }
        if (vsrs.isEmpty()) return false;
        return (vsrs[0].Status__c == null);
    }
}

