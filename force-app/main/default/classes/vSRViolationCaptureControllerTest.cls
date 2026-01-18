@IsTest
private class vSRViolationCaptureControllerTest {

    // ---------------------------
    // Test data helpers (keep your mapping)
    // ---------------------------
    private static Id createImageContentDocument(String title, String fileNameWithExt) {
        ContentVersion cv = new ContentVersion(
            Title = title,
            PathOnClient = fileNameWithExt,
            VersionData = Blob.valueOf('test-image-bytes'),
            IsMajorVersion = true
        );
        insert cv;

        cv = [
            SELECT Id, ContentDocumentId
            FROM ContentVersion
            WHERE Id = :cv.Id
            LIMIT 1
        ];
        return cv.ContentDocumentId;
    }

    private static Id latestVersionIdForContentDocument(Id contentDocumentId) {
        return [
            SELECT Id
            FROM ContentVersion
            WHERE ContentDocumentId = :contentDocumentId
            AND IsLatest = true
            LIMIT 1
        ].Id;
    }

    private static Opportunity createOpp() {
        // Use a safe stage name for most orgs
        Opportunity opp = new Opportunity(
            Name = 'Opp-' + System.now().getTime(),
            StageName = 'Prospecting',
            CloseDate = Date.today().addDays(10)
        );
        insert opp;
        return opp;
    }

    private static Case createCaseWithOpp(Boolean isChild, Id parentCaseId, Id oppId) {
        // (optional) avoid duplicate settings insert
        try {
            List<Trigger_Enabler__c> existing = [SELECT Id FROM Trigger_Enabler__c WHERE Name = 'Case' LIMIT 1];
            if (existing.isEmpty()) {
                Trigger_Enabler__c triggerEnablerSettings = new Trigger_Enabler__c();
                triggerEnablerSettings.Name = 'Case';
                triggerEnablerSettings.Is_Before_Insert__c = true;
                insert triggerEnablerSettings;
            }
        } catch (Exception ignore) {
            // ignore if object doesn't exist in a given sandbox
        }

        Id rtId;
        try {
            RecordType rt = [
                SELECT Id
                FROM RecordType
                WHERE SObjectType = 'Case'
                AND DeveloperName = 'Pre_Title_Deed_Registration'
                LIMIT 1
            ];
            rtId = rt.Id;
        } catch (Exception ignore) {
            rtId = null;
        }

        Case c = new Case();
        c.Subject = isChild ? 'Child Case' : 'Parent Case';
        c.Status = 'New';
        c.Origin = 'Phone';
        c.put('Opportunity__c', oppId);
        if (rtId != null) c.RecordTypeId = rtId;

        if (isChild) c.put('Related_Case__c', parentCaseId);

        insert c;
        return c;
    }

    private static Id createInventory() {
        Inventory__c inv = new Inventory__c();
        inv.Status__c = 'Available';
        inv.available_date__c = Date.today();
        inv.No_of_Parkings__c = 2;
        inv.Unit_Area_Sqft__c = 54.4;
        inv.UnitLocation__c = 'Prp 1';
        inv.Unit_List_Price__c = 200;
        inv.Unit_Type__c = '3 Bedroom';
        inv.Unit_Code__c = 'LM-6/1';
        insert inv;
        return inv.Id;
    }

    private static Booking_Unit__c createBookingUnit(Id oppId, Id inventoryId) {
        Booking_Unit__c bu = new Booking_Unit__c();
        bu.Booking__c = oppId;
        bu.Inventory__c = inventoryId;
        bu.Booking_Unit_Price__c = 234324;
        insert bu;
        return bu;
    }

    // Upload a file to a record and return ContentDocumentId
    private static Id uploadFileToRecord(Id firstPublishLocationId, String title, String fileNameWithExt) {
        ContentVersion cv = new ContentVersion(
            Title = title,
            PathOnClient = fileNameWithExt,
            VersionData = Blob.valueOf('bytes'),
            IsMajorVersion = true,
            FirstPublishLocationId = firstPublishLocationId
        );
        insert cv;
        return [SELECT ContentDocumentId FROM ContentVersion WHERE Id = :cv.Id LIMIT 1].ContentDocumentId;
    }

    // ---------------------------
    // Tests
    // ---------------------------
    @IsTest
    static void testGetVSRViolationRecords_smoke() {
        Test.startTest();
        List<Violation_Configuration__mdt> rows = vSRViolationCaptureController.getVSRViolationRecords();
        Test.stopTest();
        System.assertNotEquals(null, rows, 'Should return a list (can be empty).');
    }

    @IsTest
    static void testGetExisting_nullCaseId() {
        Test.startTest();
        String jsonStr = vSRViolationCaptureController.getExistingVSRWithViolations(null);
        Test.stopTest();
        Map<String, Object> outMap = (Map<String, Object>) JSON.deserializeUntyped(jsonStr);
        System.assertEquals(false, (Boolean) outMap.get('hasVsr'));
    }

    @IsTest
    static void testEnsureVsrForCase_createsDraftAndBannerTrue() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);
        Case c = createCaseWithOpp(false, null, opp.Id);

        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);
        System.assertNotEquals(null, vsrId);

        VSR__c vsr = [SELECT Id, Status__c FROM VSR__c WHERE Id = :vsrId LIMIT 1];
        System.assertEquals(null, vsr.Status__c, 'Draft VSR should have no Status__c.');
        System.assertEquals(true, VsrDraftStatusController.hasDraftVsr(c.Id));
    }

    @IsTest
    static void testCreateViolationWithPlaceholders_uploadStoresContentVersionId_andGetExistingReturnsContentDocumentId() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);
        Case c = createCaseWithOpp(false, null, opp.Id);

        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            c.Id, vsrId, 'Leak', 'Orange', 'desc', false
        );
        Id vioId = (Id) created.get('violationId');
        Id beforeDocId = (Id) created.get('beforeDocumentId');
        System.assertNotEquals(null, vioId);
        System.assertNotEquals(null, beforeDocId);

        Id cdId = uploadFileToRecord(beforeDocId, 'before', 'before.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(beforeDocId, 'before.jpg', String.valueOf(cdId));

        // Stored value should be ContentVersion.Id (requirement)
        Documents__c d = [SELECT AttachmentID__c FROM Documents__c WHERE Id = :beforeDocId LIMIT 1];
        Id storedVerId = (Id) d.AttachmentID__c;
        System.assertNotEquals(null, storedVerId);
        System.assertEquals(cdId, getContentDocumentIdFromVersion(storedVerId));

        // getExisting should return ContentDocumentId to UI
        String jsonStr = vSRViolationCaptureController.getExistingVSRWithViolations(c.Id);
        Map<String, Object> outMap = (Map<String, Object>) JSON.deserializeUntyped(jsonStr);
        System.assertEquals(true, (Boolean) outMap.get('hasVsr'));
        List<Object> rows = (List<Object>) outMap.get('rows');
        System.assertEquals(1, rows.size());
        Map<String, Object> r0 = (Map<String, Object>) rows[0];
        System.assertEquals(String.valueOf(vioId), (String) r0.get('violationRecordId'));
        System.assertEquals(String.valueOf(cdId), (String) r0.get('contentDocumentId'));
    }

    private static Id getContentDocumentIdFromVersion(Id versionId) {
        return [SELECT ContentDocumentId FROM ContentVersion WHERE Id = :versionId LIMIT 1].ContentDocumentId;
    }

    @IsTest
    static void testFollowUp_autoCopiesParentViolations_andCarriesBeforeAfterNoc() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);

        // Parent
        Case parentCase = createCaseWithOpp(false, null, opp.Id);
        Id parentVsrId = vSRViolationCaptureController.ensureVsrForCase(parentCase.Id);

        Map<String, Object> created = vSRViolationCaptureController.createViolationWithPlaceholders(
            parentCase.Id, parentVsrId, 'Parent Violation', 'Red', 'desc', false
        );
        Id pBeforeDocId = (Id) created.get('beforeDocumentId');
        Id pAfterDocId  = (Id) created.get('afterDocumentId');
        Id pNocDocId    = (Id) created.get('nocDocumentId');

        Id pBeforeCd = uploadFileToRecord(pBeforeDocId, 'pbefore', 'pbefore.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pBeforeDocId, 'pbefore.jpg', String.valueOf(pBeforeCd));
        Id pAfterCd = uploadFileToRecord(pAfterDocId, 'pafter', 'pafter.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pAfterDocId, 'pafter.jpg', String.valueOf(pAfterCd));
        Id pNocCd = uploadFileToRecord(pNocDocId, 'pnoc', 'pnoc.jpg');
        vSRViolationCaptureController.updateViolationDocumentAfterUpload(pNocDocId, 'pnoc.jpg', String.valueOf(pNocCd));

        // Child follow-up: ensureVsrForCase will auto-copy + carry files down
        Case childCase = createCaseWithOpp(true, parentCase.Id, opp.Id);
        Id childVsrId = vSRViolationCaptureController.ensureVsrForCase(childCase.Id);

        List<Violation__c> childVios = [
            SELECT Id, New_Violation__c
            FROM Violation__c
            WHERE VSR__c = :childVsrId
        ];
        System.assertEquals(1, childVios.size());
        System.assertEquals(false, childVios[0].New_Violation__c);

        List<Documents__c> childDocs = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Violation__c = :childVios[0].Id
        ];
        System.assertEquals(3, childDocs.size());
        for (Documents__c d : childDocs) {
            System.assertNotEquals(null, d.AttachmentID__c, 'Before/After/NOC should be carried down if present in parent.');
            // stored is ContentVersion Id
            Id verId = (Id) d.AttachmentID__c;
            System.assertNotEquals(null, getContentDocumentIdFromVersion(verId));
        }
    }

    @IsTest
    static void testNewViolationFlag_setOnFollowUpAdd() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);

        Case parentCase = createCaseWithOpp(false, null, opp.Id);
        vSRViolationCaptureController.ensureVsrForCase(parentCase.Id);

        Case childCase = createCaseWithOpp(true, parentCase.Id, opp.Id);
        Id childVsrId = vSRViolationCaptureController.ensureVsrForCase(childCase.Id);

        Map<String, Object> createdNew = vSRViolationCaptureController.createViolationWithPlaceholders(
            childCase.Id, childVsrId, 'NewInChild', 'Yellow', 'd', true
        );
        Id newVioId = (Id) createdNew.get('violationId');
        Violation__c v = [SELECT New_Violation__c FROM Violation__c WHERE Id = :newVioId LIMIT 1];
        System.assertEquals(true, v.New_Violation__c);
    }

    @IsTest
    static void testViolationNumberingAndRenumberOnDelete() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);
        Case c = createCaseWithOpp(false, null, opp.Id);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'Y1', 'Yellow', 'd', false);
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'O1', 'Orange', 'd', false);
        vSRViolationCaptureController.createViolationWithPlaceholders(c.Id, vsrId, 'Y2', 'Yellow', 'd', false);

        Map<String, String> byHeading = new Map<String, String>();
        for (Violation__c v : [
            SELECT Heading__c, Violation_Number__c
            FROM Violation__c
            WHERE VSR__c = :vsrId
        ]) {
            byHeading.put(v.Heading__c, v.Violation_Number__c);
        }
        System.assertEquals('ITEM A.1', byHeading.get('Y1'));
        System.assertEquals('ITEM B.1', byHeading.get('O1'));
        System.assertEquals('ITEM A.2', byHeading.get('Y2'));

        Violation__c y1 = [SELECT Id FROM Violation__c WHERE VSR__c = :vsrId AND Heading__c = 'Y1' LIMIT 1];
        vSRViolationCaptureController.deleteViolationAndFiles(y1.Id);

        Violation__c y2 = [SELECT Violation_Number__c FROM Violation__c WHERE VSR__c = :vsrId AND Heading__c = 'Y2' LIMIT 1];
        System.assertEquals('ITEM A.1', y2.Violation_Number__c);
    }

    @IsTest
    static void testSubmitVsr_setsStatus_andValidatesVillaForClear() {
        Id invId = createInventory();
        Opportunity opp = createOpp();
        createBookingUnit(opp.Id, invId);
        Case c = createCaseWithOpp(false, null, opp.Id);
        Id vsrId = vSRViolationCaptureController.ensureVsrForCase(c.Id);

        // Clear case (no rows) requires villa
        Boolean threw = false;
        try {
            vSRViolationCaptureController.submitVSR(
                c.Id,
                JSON.serialize(new List<Object>()),
                null,
                JSON.serialize(new List<String>()),
                JSON.serialize(new List<String>())
            );
        } catch (AuraHandledException e) {
            threw = true;
        }
        System.assertEquals(true, threw, 'Should require villa picture when submitting Clear with no violations.');

        // Upload villa and resubmit
        Id villaDocId = vSRViolationCaptureController.ensureVillaDocumentForVsr(vsrId);
        Id villaCdId = uploadFileToRecord(villaDocId, 'villa', 'villa.jpg');
        vSRViolationCaptureController.updateVillaPictureAfterUpload(villaDocId, 'villa.jpg', String.valueOf(villaCdId));

        Test.startTest();
        Id savedVsrId = vSRViolationCaptureController.submitVSR(
            c.Id,
            JSON.serialize(new List<Object>()),
            villaDocId,
            JSON.serialize(new List<String>()),
            JSON.serialize(new List<String>())
        );
        Test.stopTest();

        VSR__c saved = [SELECT Status__c, Issued_date__c FROM VSR__c WHERE Id = :savedVsrId LIMIT 1];
        System.assertEquals('Clear', saved.Status__c);
        System.assertEquals(Date.today(), saved.Issued_date__c);
    }

    @IsTest
    static void testGetThumbnailUrls_returnsForImages() {
        Id cdId = createImageContentDocument('thumb', 'thumb.jpg');
        Test.startTest();
        Map<Id, String> urls = vSRViolationCaptureController.getThumbnailUrls(new List<Id>{ cdId });
        Test.stopTest();

        System.assertNotEquals(null, urls);
        System.assertEquals(true, urls.containsKey(cdId), 'Should return a thumbnail URL for jpg.');
        System.assert(String.valueOf(urls.get(cdId)).contains('renditionDownload'));
    }
}

