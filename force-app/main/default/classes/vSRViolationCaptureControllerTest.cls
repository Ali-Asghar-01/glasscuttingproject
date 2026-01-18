@IsTest
public class vSRViolationCaptureControllerTest {
    private static Id createInventory() {
        SObject inv = Schema.getGlobalDescribe().get('Inventory__c').newSObject();
        Map<String, Schema.SObjectField> fields = inv.getSObjectType().getDescribe().fields.getMap();

        if (fields.containsKey('Name')) inv.put('Name', 'Test Inventory');
        if (fields.containsKey('Status__c')) inv.put('Status__c', 'Available');
        if (fields.containsKey('available_date__c')) inv.put('available_date__c', Date.today());
        if (fields.containsKey('No_of_Parkings__c')) inv.put('No_of_Parkings__c', 1);
        if (fields.containsKey('Unit_Area_Sqft__c')) inv.put('Unit_Area_Sqft__c', 50.0);
        if (fields.containsKey('UnitLocation__c')) inv.put('UnitLocation__c', 'Test');
        if (fields.containsKey('Unit_List_Price__c')) inv.put('Unit_List_Price__c', 100);
        if (fields.containsKey('Unit_Type__c')) inv.put('Unit_Type__c', '3 Bedroom');
        if (fields.containsKey('Unit_Code__c')) inv.put('Unit_Code__c', 'UNIT-1');

        insert inv;
        return (Id) inv.get('Id');
    }

    private static void createBookingUnit(Id oppId, Id inventoryId) {
        SObject bu = Schema.getGlobalDescribe().get('Booking_Unit__c').newSObject();
        bu.put('Booking__c', oppId);
        bu.put('Inventory__c', inventoryId);
        Map<String, Schema.SObjectField> fields = bu.getSObjectType().getDescribe().fields.getMap();
        // some orgs require Name or price
        if (fields.containsKey('Name')) {
            bu.put('Name', 'BU');
        }
        if (fields.containsKey('Booking_Unit_Price__c')) {
            bu.put('Booking_Unit_Price__c', 100);
        }
        insert bu;
    }

    private static Case createCaseWithOpp(Boolean isFollowUp, Id parentCaseId) {
        Account a = new Account(Name = 'Test Acc');
        insert a;

        Opportunity opp = new Opportunity(
            Name = 'Test Opp',
            StageName = 'Prospecting',
            CloseDate = Date.today(),
            AccountId = a.Id
        );
        insert opp;

        Id invId = createInventory();
        createBookingUnit(opp.Id, invId);

        Case c = new Case(
            Subject = 'Test Case',
            Status = 'New',
            Origin = 'Phone'
        );
        // custom field used by controller
        c.put('Opportunity__c', opp.Id);
        if (isFollowUp && parentCaseId != null) {
            c.put('Related_Case__c', parentCaseId);
        }
        insert c;
        return c;
    }

    private static Id uploadFileToRecord(Id firstPublishLocationId, String fileName) {
        ContentVersion cv = new ContentVersion();
        cv.Title = fileName;
        cv.PathOnClient = fileName;
        cv.VersionData = Blob.valueOf('unit test content');
        cv.FirstPublishLocationId = firstPublishLocationId;
        insert cv;
        return [
            SELECT ContentDocumentId
            FROM ContentVersion
            WHERE Id = :cv.Id
            LIMIT 1
        ].ContentDocumentId;
    }

    private static Id getLatestVersionId(Id contentDocumentId) {
        return [
            SELECT Id
            FROM ContentVersion
            WHERE ContentDocumentId = :contentDocumentId
            AND IsLatest = true
            LIMIT 1
        ].Id;
    }

    private static Id getContentDocumentIdFromVersion(Id versionId) {
        return [
            SELECT ContentDocumentId
            FROM ContentVersion
            WHERE Id = :versionId
            LIMIT 1
        ].ContentDocumentId;
    }

    @IsTest
    static void testDraftBanner_hasDraftVsr() {
        Case c = createCaseWithOpp(false, null);
        Test.startTest();
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);
        Test.stopTest();

        System.assertNotEquals(null, vsrId);
        System.assertEquals(true, VsrDraftStatusController.hasDraftVsr(c.Id));
    }

    @IsTest
    static void testGetExisting_nullCaseId() {
        String jsonStr = vSRViolationCaptureController.getExistingVSRWithViolations(null);
        Map<String, Object> outMap = (Map<String, Object>) JSON.deserializeUntyped(jsonStr);
        System.assertEquals(false, (Boolean) outMap.get('hasVsr'));
    }

    @IsTest
    static void testCreateViolationPlaceholdersAndAttachmentStorageAsContentVersion() {
        Case c = createCaseWithOpp(false, null);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            c.Id,
            vsrId,
            'Test Violation',
            'Yellow',
            'Desc',
            false
        );

        Id beforeDocId = (Id) created.get('beforeDocumentId');
        Id afterDocId  = (Id) created.get('afterDocumentId');
        Id nocDocId    = (Id) created.get('nocDocumentId');

        System.assertNotEquals(null, beforeDocId);
        System.assertNotEquals(null, afterDocId);
        System.assertNotEquals(null, nocDocId);

        // Attach BEFORE
        Id beforeCdId = uploadFileToRecord(beforeDocId, 'before.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(beforeDocId, 'before.jpg', String.valueOf(beforeCdId));

        // AttachmentID__c should now store ContentVersion.Id
        Documents__c beforeDoc = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Id = :beforeDocId
            LIMIT 1
        ];
        System.assertNotEquals(null, beforeDoc.AttachmentID__c);

        // AttachmentID__c points to ContentVersion, which points to a ContentDocument
        Id storedVersionId = (Id) beforeDoc.AttachmentID__c;
        ContentVersion stored = [
            SELECT Id, ContentDocumentId
            FROM ContentVersion
            WHERE Id = :storedVersionId
            LIMIT 1
        ];
        System.assertNotEquals(null, stored.ContentDocumentId);

        // Attach AFTER + NOC too (for carry-forward test later)
        Id afterCdId = uploadFileToRecord(afterDocId, 'after.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(afterDocId, 'after.jpg', String.valueOf(afterCdId));

        Id nocCdId = uploadFileToRecord(nocDocId, 'noc.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(nocDocId, 'noc.jpg', String.valueOf(nocCdId));
    }

    @IsTest
    static void testFollowUpCopiesParentViolationsAndDocs() {
        // Parent case + VSR + one violation with before/after/noc files
        Case parentCase = createCaseWithOpp(false, null);
        Id parentVsrId = vSRViolationCaptureController.ensureVsrForCase(parentCase.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            parentCase.Id,
            parentVsrId,
            'Parent Violation',
            'Red',
            'Parent Desc',
            false
        );

        Id pBeforeDocId = (Id) created.get('beforeDocumentId');
        Id pAfterDocId  = (Id) created.get('afterDocumentId');
        Id pNocDocId    = (Id) created.get('nocDocumentId');

        Id pBeforeCdId = uploadFileToRecord(pBeforeDocId, 'pbefore.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pBeforeDocId, 'pbefore.jpg', String.valueOf(pBeforeCdId));

        Id pAfterCdId = uploadFileToRecord(pAfterDocId, 'pafter.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pAfterDocId, 'pafter.jpg', String.valueOf(pAfterCdId));

        Id pNocCdId = uploadFileToRecord(pNocDocId, 'pnoc.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pNocDocId, 'pnoc.jpg', String.valueOf(pNocCdId));

        // Child follow-up case
        Case childCase = createCaseWithOpp(true, parentCase.Id);
        Id childVsrId = vSRViolationCaptureController.ensureVsrForCase(childCase.Id);

        List<Violation__c> childVios = [
            SELECT Id, New_Violation__c
            FROM Violation__c
            WHERE VSR__c = :childVsrId
        ];
        System.assertEquals(1, childVios.size(), 'Parent violation should be copied into follow-up VSR');
        System.assertEquals(false, childVios[0].New_Violation__c, 'Copied violation must not be marked as New');

        // Query all docs and assert attachments exist
        List<Documents__c> childDocs = [
            SELECT Id, Document_Type__c, AttachmentID__c
            FROM Documents__c
            WHERE Violation__c = :childVios[0].Id
        ];
        // 3 placeholders (Before/After/NOC) and all should have files carried down if present on parent
        System.assertEquals(3, childDocs.size());
        for (Documents__c d : childDocs) {
            System.assertNotEquals(null, d.AttachmentID__c, 'Copied docs should carry down files where available');
            // stored as ContentVersion Id
            Id verId = (Id) d.AttachmentID__c;
            System.assertNotEquals(null, [SELECT ContentDocumentId FROM ContentVersion WHERE Id = :verId LIMIT 1].ContentDocumentId);
        }
    }

    @IsTest
    static void testGetExisting_followUpLoadsChildRowsNotBaseline() {
        // parent with 1 violation + docs
        Case parentCase = createCaseWithOpp(false, null);
        Id parentVsrId = vSRViolationCaptureController.ensureVsrForCase(parentCase.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            parentCase.Id,
            parentVsrId,
            'P1',
            'Orange',
            'Desc',
            false
        );
        Id pBeforeDocId = (Id) created.get('beforeDocumentId');
        Id pAfterDocId  = (Id) created.get('afterDocumentId');
        Id pNocDocId    = (Id) created.get('nocDocumentId');

        Id pBeforeCdId = uploadFileToRecord(pBeforeDocId, 'pbefore.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pBeforeDocId, 'pbefore.jpg', String.valueOf(pBeforeCdId));
        Id pAfterCdId = uploadFileToRecord(pAfterDocId, 'pafter.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pAfterDocId, 'pafter.jpg', String.valueOf(pAfterCdId));
        Id pNocCdId = uploadFileToRecord(pNocDocId, 'pnoc.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pNocDocId, 'pnoc.jpg', String.valueOf(pNocCdId));

        // child follow-up triggers copy on ensureVsrForCase
        Case childCase = createCaseWithOpp(true, parentCase.Id);
        vSRViolationCaptureController.ensureVsrForCase(childCase.Id);

        String jsonStr = vSRViolationCaptureController.getExistingVSRWithViolations(childCase.Id);
        Map<String, Object> outMap = (Map<String, Object>) JSON.deserializeUntyped(jsonStr);
        System.assertEquals(true, (Boolean) outMap.get('hasVsr'));
        System.assertEquals(true, (Boolean) outMap.get('isFollowUp'));
        System.assertEquals(false, (Boolean) outMap.get('sourceIsParentBaseline'), 'We copy parent rows into child, so baseline mode should not apply.');

        List<Object> rows = (List<Object>) outMap.get('rows');
        System.assertEquals(1, rows.size());
        Map<String, Object> r0 = (Map<String, Object>) rows[0];
        System.assertEquals(true, (Boolean) r0.get('isCarriedForward'));
        System.assertNotEquals(null, r0.get('beforeContentDocumentId'));
        System.assertNotEquals(null, r0.get('afterContentDocumentId'));
    }

    @IsTest
    static void testSubmitVsr_updatesStatusAndRequiresVillaForClear() {
        Case c = createCaseWithOpp(false, null);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        // Add one Yellow violation with before doc file (so Non-Compliant expected)
        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            c.Id, vsrId, 'Y1', 'Yellow', 'd', false
        );
        Id beforeDocId = (Id) created.get('beforeDocumentId');
        Id beforeCdId = uploadFileToRecord(beforeDocId, 'before.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(beforeDocId, 'before.jpg', String.valueOf(beforeCdId));

        // Upload villa picture (required for Clear/Clear with Comments; but Non-Compliant doesn't require it.
        // Still upload to cover villa path.)
        Id villaDocId = vSRViolationCaptureController.ensureVillaDocumentForVsr(vsrId);
        Id villaCdId = uploadFileToRecord(villaDocId, 'villa.jpg');
        vSRViolationCaptureController.updateVillaPictureAfterUpload(villaDocId, 'villa.jpg', String.valueOf(villaCdId));

        List<Map<String, Object>> payload = new List<Map<String, Object>>{
            new Map<String, Object>{
                'violationRecordId' => String.valueOf((Id) created.get('violationId')),
                'documentId' => String.valueOf(beforeDocId),
                'name' => 'Y1',
                'category' => 'Yellow',
                'description' => 'd',
                'isFixed' => false
            }
        };

        Test.startTest();
        Id savedVsrId = vSRViolationCaptureController.submitVSR(
            c.Id,
            JSON.serialize(payload),
            villaDocId,
            JSON.serialize(new List<String>()),
            JSON.serialize(new List<String>())
        );
        Test.stopTest();

        VSR__c saved = [SELECT Status__c, Issued_date__c FROM VSR__c WHERE Id = :savedVsrId LIMIT 1];
        System.assertEquals('Non-Compliant', saved.Status__c);
        System.assertEquals(Date.today(), saved.Issued_date__c);
    }

    @IsTest
    static void testGetThumbnailUrls_andClearDocumentFile() {
        Case c = createCaseWithOpp(false, null);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            c.Id, vsrId, 'T1', 'Orange', 'd', false
        );
        Id beforeDocId = (Id) created.get('beforeDocumentId');

        Id cdId = uploadFileToRecord(beforeDocId, 'thumb.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(beforeDocId, 'thumb.jpg', String.valueOf(cdId));

        Map<Id, String> urls = vSRViolationCaptureController.getThumbnailUrls(new List<Id>{ cdId });
        System.assertEquals(true, urls.containsKey(cdId));
        System.assert(String.valueOf(urls.get(cdId)).contains('renditionDownload'));

        // Clear document file
        vSRViolationCaptureController.clearDocumentFile(beforeDocId);
        Documents__c docAfter = [SELECT AttachmentID__c FROM Documents__c WHERE Id = :beforeDocId LIMIT 1];
        System.assertEquals(null, docAfter.AttachmentID__c);
    }

    @IsTest
    static void testViolationNumberingAndRenumberOnDelete() {
        Case c = createCaseWithOpp(false, null);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        // Insert 2 yellows + 1 orange + 1 red
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'Y1', 'Yellow', 'd', false);
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'O1', 'Orange', 'd', false);
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'Y2', 'Yellow', 'd', false);
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'R1', 'Red', 'd', false);

        List<Violation__c> vios = [
            SELECT Id, Heading__c, Category__c, Violation_Number__c
            FROM Violation__c
            WHERE VSR__c = :vsrId
            ORDER BY CreatedDate ASC
        ];
        System.assert(vios.size() >= 4);

        // Verify expected numbering exists
        Map<String, String> byHeading = new Map<String, String>();
        for (Violation__c v : vios) byHeading.put(v.Heading__c, v.Violation_Number__c);

        System.assertEquals('ITEM A.1', byHeading.get('Y1'));
        System.assertEquals('ITEM B.1', byHeading.get('O1'));
        System.assertEquals('ITEM A.2', byHeading.get('Y2'));
        System.assertEquals('ITEM C.1', byHeading.get('R1'));

        // Delete first yellow, ensure remaining yellow becomes A.1
        Violation__c y1 = [SELECT Id FROM Violation__c WHERE VSR__c = :vsrId AND Heading__c = 'Y1' LIMIT 1];
        vSRViolationCaptureController.deleteViolationAndFiles(y1.Id);

        Violation__c y2 = [SELECT Violation_Number__c FROM Violation__c WHERE VSR__c = :vsrId AND Heading__c = 'Y2' LIMIT 1];
        System.assertEquals('ITEM A.1', y2.Violation_Number__c);
    }

    @IsTest
    static void testNewViolationFlag_setOnFollowUpAdds() {
        Case parentCase = createCaseWithOpp(false, null);
        vSRViolationCaptureController.ensureVsrForCase(parentCase.Id);

        Case childCase = createCaseWithOpp(true, parentCase.Id);
        Id childVsrId = vSRViolationCaptureController.ensureVsrForCase(childCase.Id);

        Map<String, Object> createdNew = vSRViolationCaptureController.createViolationWithPlaceholders(
            childCase.Id,
            childVsrId,
            'NewInChild',
            'Orange',
            'd',
            true
        );
        Id newVioId = (Id) createdNew.get('violationId');
        Violation__c v = [SELECT New_Violation__c FROM Violation__c WHERE Id = :newVioId LIMIT 1];
        System.assertEquals(true, v.New_Violation__c);
    }
}

