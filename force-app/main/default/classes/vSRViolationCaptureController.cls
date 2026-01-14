public with sharing class vSRViolationCaptureController {

    // ============================================================
    // VSR (single per Case) - create draft on modal open
    // Draft definition per requirement: Status__c is NULL (and Issued_date__c is NULL).
    // ============================================================
    @AuraEnabled
    public static Id ensureVsrForCase(Id caseId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');

        List<VSR__c> existing = [
            SELECT Id, Case__c, Status__c, Issued_date__c
            FROM VSR__c
            WHERE Case__c = :caseId
            LIMIT 2
        ];
        if (existing.size() > 1) {
            throw new AuraHandledException('More than one VSR exists for this Case. Please contact support.');
        }
        if (!existing.isEmpty()) return existing[0].Id;

        Case caseRec = [
            SELECT Id, Opportunity__c
            FROM Case
            WHERE Id = :caseId
            LIMIT 1
        ];
        if (caseRec.Opportunity__c == null) throw new AuraHandledException('No Opportunity is linked to this Case.');

        Booking_Unit__c bu = [
            SELECT Id, Inventory__c
            FROM Booking_Unit__c
            WHERE Booking__c = :caseRec.Opportunity__c
            LIMIT 1
        ];
        if (bu.Inventory__c == null) throw new AuraHandledException('No Inventory is linked to the Booking Unit.');

        if (!Schema.sObjectType.VSR__c.isCreateable()) {
            throw new AuraHandledException('You do not have permission to create VSR records.');
        }

        // Draft VSR: do not set Status__c or Issued_date__c
        VSR__c vsr = new VSR__c(
            Case__c = caseId,
            Inventory__c = bu.Inventory__c
        );
        insert vsr;
        return vsr.Id;
    }

    @AuraEnabled(cacheable=true)
    public static List<Violation_Configuration__mdt> getVSRViolationRecords() {
        return [
            SELECT Id, Label, DeveloperName, Category__c, Description__c, Heading__c, Type__c
            FROM Violation_Configuration__mdt
            WHERE Type__c = 'VSR'
            ORDER BY Label
        ];
    }

    @AuraEnabled(cacheable=true)
    public static String getExistingVSRWithViolations(Id caseId) {
        if (caseId == null) {
            return JSON.serialize(new Map<String, Object>{ 'hasVsr' => false });
        }

        Case c = [
            SELECT Id, Related_Case__c
            FROM Case
            WHERE Id = :caseId
            LIMIT 1
        ];
        Boolean isFollowUp = (c.Related_Case__c != null);

        // Single VSR per Case
        List<VSR__c> childVsrs = [
            SELECT Id, Case__c, Status__c, Issued_date__c, CreatedDate
            FROM VSR__c
            WHERE Case__c = :caseId
            LIMIT 2
        ];
        if (childVsrs.size() > 1) {
            throw new AuraHandledException('More than one VSR exists for this Case. Please contact support.');
        }
        VSR__c currentVsr = childVsrs.isEmpty() ? null : childVsrs[0];

        // Parent VSR (for baseline rows display only)
        VSR__c parentVsr = null;
        if (isFollowUp) {
            List<VSR__c> parentVsrs = [
                SELECT Id, Case__c, Status__c, Issued_date__c, CreatedDate
                FROM VSR__c
                WHERE Case__c = :c.Related_Case__c
                LIMIT 2
            ];
            if (parentVsrs.size() > 1) {
                throw new AuraHandledException('More than one VSR exists for the parent Case. Please contact support.');
            }
            if (!parentVsrs.isEmpty()) parentVsr = parentVsrs[0];
        }

        if (currentVsr == null) {
            // No VSR created yet (ensureVsrForCase should be called by UI on open)
            return JSON.serialize(new Map<String, Object>{
                'hasVsr' => false,
                'isFollowUp' => isFollowUp,
                'sourceIsParentBaseline' => false,
                'vsrCaseId' => null,
                'villaDocumentId' => null,
                'villaFileName' => 'Villa Picture',
                'villaContentDocumentId' => null,
                'rows' => new List<Object>()
            });
        }

        // Baseline rows from parent are shown only if follow-up AND current VSR has no violations yet.
        Integer childViolationCount = [
            SELECT COUNT()
            FROM Violation__c
            WHERE VSR__c = :currentVsr.Id
        ];
        Boolean baselineMode = isFollowUp && parentVsr != null && childViolationCount == 0;

        // -----------------------------
        // Villa (NO carry-forward):
        // - show current-case file only
        // - return villaDocumentId only if it exists (do not create placeholder on open)
        // -----------------------------
        Documents__c currentVillaDoc = getVillaDocForCase(caseId);

        Id currentVillaCd = null;
        if (currentVillaDoc != null && !String.isBlank(currentVillaDoc.AttachmentID__c)) {
            try { currentVillaCd = (Id) currentVillaDoc.AttachmentID__c; } catch (Exception ignore) {}
        }

        Id villaCdToShow = currentVillaCd;
        String villaDisplayName = getFileTitleSafe(villaCdToShow);
        if (String.isBlank(villaDisplayName)) villaDisplayName = 'Villa Picture';

        // Load violations:
        // - baselineMode: load parent VSR violations as read-only baseline rows (no delete/category edits in UI)
        // - else: load current VSR violations
        Id vsrToLoadId = baselineMode ? parentVsr.Id : currentVsr.Id;
        List<Violation__c> violations = [
            SELECT Id, Heading__c, Category__c, Description__c, Is_Fixed__c
            FROM Violation__c
            WHERE VSR__c = :vsrToLoadId
            ORDER BY CreatedDate ASC
        ];

        Set<Id> violationIds = new Set<Id>();
        for (Violation__c v : violations) violationIds.add(v.Id);

        Map<Id, Documents__c> bestBefore = new Map<Id, Documents__c>();
        Map<Id, Documents__c> bestAfter  = new Map<Id, Documents__c>();
        Map<Id, Documents__c> bestLegacy = new Map<Id, Documents__c>();

        if (!violationIds.isEmpty()) {
            for (Documents__c d : [
                SELECT Id, Name, AttachmentID__c, Violation__c, Document_Type__c, CreatedDate
                FROM Documents__c
                WHERE Violation__c IN :violationIds
                ORDER BY CreatedDate DESC
            ]) {
                if (d.Violation__c == null) continue;

                if (d.Document_Type__c == 'VSR Violation Before Evidence') {
                    if (!bestBefore.containsKey(d.Violation__c)) bestBefore.put(d.Violation__c, d);
                } else if (d.Document_Type__c == 'VSR Violation After Evidence') {
                    if (!bestAfter.containsKey(d.Violation__c)) bestAfter.put(d.Violation__c, d);
                } else {
                    if (!bestLegacy.containsKey(d.Violation__c)) bestLegacy.put(d.Violation__c, d);
                }
            }
        }

        List<Map<String, Object>> rows = new List<Map<String, Object>>();

        for (Violation__c v : violations) {
            if (!isFollowUp) {
                Documents__c docPick = bestBefore.get(v.Id);
                if (docPick == null) docPick = bestLegacy.get(v.Id);

                rows.add(new Map<String, Object>{
                    'violationRecordId' => v.Id,
                    'name' => v.Heading__c,
                    'category' => v.Category__c,
                    'description' => v.Description__c,
                    'isFixed' => v.Is_Fixed__c,
                    'isFixedOnLoad' => v.Is_Fixed__c,
                    'documentId' => (docPick != null ? docPick.Id : null),
                    'contentDocumentId' => (docPick != null ? docPick.AttachmentID__c : null),
                    'fileName' => (docPick != null ? docPick.Name : null)
                });
                continue;
            }

            if (baselineMode) {
                Documents__c beforePick = bestBefore.get(v.Id);
                if (beforePick == null) beforePick = bestLegacy.get(v.Id);

                rows.add(new Map<String, Object>{
                    'violationRecordId' => null,
                    'sourceViolationRecordId' => v.Id,

                    'name' => v.Heading__c,
                    'category' => v.Category__c,
                    'description' => v.Description__c,

                    'isFixed' => v.Is_Fixed__c,
                    'isFixedOnLoad' => v.Is_Fixed__c,

                    'isCarriedForward' => true,
                    'isParentBaselineRow' => true,

                    'beforeDocumentId' => (beforePick != null ? beforePick.Id : null),
                    'beforeContentDocumentId' => (beforePick != null ? beforePick.AttachmentID__c : null),
                    'beforeFileName' => (beforePick != null ? beforePick.Name : null),

                    'afterDocumentId' => null,
                    'afterContentDocumentId' => null,
                    'afterFileName' => null
                });
            } else {
                Documents__c beforePick = bestBefore.get(v.Id);
                if (beforePick == null) beforePick = bestLegacy.get(v.Id);

                Documents__c afterPick = bestAfter.get(v.Id);
                Boolean isCarriedForward = (afterPick != null);

                rows.add(new Map<String, Object>{
                    'violationRecordId' => v.Id,
                    'sourceViolationRecordId' => null,

                    'name' => v.Heading__c,
                    'category' => v.Category__c,
                    'description' => v.Description__c,

                    'isFixed' => v.Is_Fixed__c,
                    'isFixedOnLoad' => v.Is_Fixed__c,

                    'isCarriedForward' => isCarriedForward,

                    'beforeDocumentId' => (beforePick != null ? beforePick.Id : null),
                    'beforeContentDocumentId' => (beforePick != null ? beforePick.AttachmentID__c : null),
                    'beforeFileName' => (beforePick != null ? beforePick.Name : null),

                    'afterDocumentId' => (afterPick != null ? afterPick.Id : null),
                    'afterContentDocumentId' => (afterPick != null ? afterPick.AttachmentID__c : null),
                    'afterFileName' => (afterPick != null ? afterPick.Name : null)
                });
            }
        }

        return JSON.serialize(new Map<String, Object>{
            'hasVsr' => true,
            // Always return current-case VSR id (uploads target this VSR)
            'vsrId' => currentVsr.Id,
            'vsrCaseId' => caseId,
            'isFollowUp' => isFollowUp,
            'sourceIsParentBaseline' => baselineMode,

            'villaDocumentId' => (currentVillaDoc != null ? currentVillaDoc.Id : null),
            'villaFileName' => villaDisplayName,
            'villaContentDocumentId' => (villaCdToShow != null ? String.valueOf(villaCdToShow) : null),

            'rows' => rows
        });
    }

    @AuraEnabled
    public static Id submitVSR(
        Id caseId,
        String rowsJson,
        Id villaDocumentId,
        String deletedViolationIdsJson,
        String deletedDocumentIdsJson
    ) {
        if (caseId == null) {
            throw new AuraHandledException('Case Id is required.');
        }

        List<Object> rows = new List<Object>();
        if (!String.isBlank(rowsJson)) {
            Object parsed = JSON.deserializeUntyped(rowsJson);
            if (parsed instanceof List<Object>) rows = (List<Object>) parsed;
        }

        Case caseRec = [
            SELECT Id, Related_Case__c, Opportunity__c
            FROM Case
            WHERE Id = :caseId
            LIMIT 1
        ];
        Boolean hasParentCase = (caseRec.Related_Case__c != null);
        Boolean isParentPayload = hasParentCase;

        if (!rows.isEmpty()) {
            validateRowsHaveRequiredFiles(rows, hasParentCase);
        }

        Set<Id> deletedViolationIds = new Set<Id>();
        Set<Id> deletedDocumentIds = new Set<Id>();

        if (!String.isBlank(deletedViolationIdsJson)) {
            for (Object o : (List<Object>) JSON.deserializeUntyped(deletedViolationIdsJson)) {
                if (o == null) continue;
                try { deletedViolationIds.add((Id) String.valueOf(o)); } catch (Exception ignore) {}
            }
        }
        if (!String.isBlank(deletedDocumentIdsJson)) {
            for (Object o : (List<Object>) JSON.deserializeUntyped(deletedDocumentIdsJson)) {
                if (o == null) continue;
                try { deletedDocumentIds.add((Id) String.valueOf(o)); } catch (Exception ignore) {}
            }
        }

        Boolean anyRY = false, anyOrange = false;
        Boolean cf_anyRY = false, cf_anyOrange = false, cf_ryNotFixed = false, cf_orangeNotFixed = false;
        Boolean new_anyRY = false, new_anyOrange = false;

        for (Object o : rows) {
            if (!(o instanceof Map<String, Object>)) continue;
            Map<String, Object> row = (Map<String, Object>) o;

            String category = (String) row.get('category');
            String cat = String.isBlank(category) ? '' : category.trim().toLowerCase();

            Boolean isFixed = readBool(row, 'isFixed');
            Boolean isCarriedForward = hasParentCase ? readIsCarriedForward(row, hasParentCase) : false;

            if (!hasParentCase) {
                if (cat == 'red' || cat == 'yellow') anyRY = true;
                if (cat == 'orange') anyOrange = true;
                continue;
            }

            if (isCarriedForward) {
                if (cat == 'red' || cat == 'yellow') {
                    cf_anyRY = true;
                    if (!isFixed) cf_ryNotFixed = true;
                }
                if (cat == 'orange') {
                    cf_anyOrange = true;
                    if (!isFixed) cf_orangeNotFixed = true;
                }
            } else {
                if (cat == 'red' || cat == 'yellow') new_anyRY = true;
                if (cat == 'orange') new_anyOrange = true;
            }
        }

        String status;
        if (!hasParentCase) {
            if (rows.isEmpty()) status = 'Clear';
            else if (anyRY) status = 'Non-Compliant';
            else if (anyOrange) status = 'Clear with Comments';
            else status = 'Clear';
        } else {
            if (!cf_anyRY && !cf_anyOrange) status = 'Clear';
            else if (!cf_anyRY && cf_anyOrange) status = cf_orangeNotFixed ? 'Clear with Comments' : 'Clear';
            else status = cf_ryNotFixed ? 'Non-Compliant' : (cf_orangeNotFixed ? 'Clear with Comments' : 'Clear');

            if (status != 'Non-Compliant') {
                if (new_anyRY) status = 'Non-Compliant';
                else if (new_anyOrange) status = 'Clear with Comments';
            }
        }

        Id oppId = caseRec.Opportunity__c;
        if (oppId == null) throw new AuraHandledException('No Opportunity is linked to this Case.');

        Booking_Unit__c bu = [
            SELECT Id, Inventory__c
            FROM Booking_Unit__c
            WHERE Booking__c = :oppId
            LIMIT 1
        ];
        Id inventoryId = bu.Inventory__c;
        if (inventoryId == null) throw new AuraHandledException('No Inventory is linked to the Booking Unit.');

        // Single VSR per Case
        List<VSR__c> existing = [
            SELECT Id, Inventory__c, Status__c, Issued_date__c
            FROM VSR__c
            WHERE Case__c = :caseId
            LIMIT 2
        ];
        if (existing.size() > 1) {
            throw new AuraHandledException('More than one VSR exists for this Case. Please contact support.');
        }

        VSR__c vsr;
        if (!existing.isEmpty()) {
            if (!Schema.sObjectType.VSR__c.isUpdateable()) {
                throw new AuraHandledException('You do not have permission to update VSR records.');
            }
            vsr = existing[0];
            vsr.Inventory__c = inventoryId;
            vsr.Status__c = status;
            vsr.Issued_date__c = Date.today();
            update vsr;
        } else {
            if (!Schema.sObjectType.VSR__c.isCreateable()) {
                throw new AuraHandledException('You do not have permission to create VSR records.');
            }
            vsr = new VSR__c(
                Case__c = caseId,
                Inventory__c = inventoryId,
                Status__c = status,
                Issued_date__c = Date.today()
            );
            insert vsr;
        }

        Documents__c ensuredVillaDoc = ensureVillaPictureForSubmit(caseRec.Id, vsr.Id, villaDocumentId);

        Boolean requireVilla = rows.isEmpty() || status == 'Clear' || status == 'Clear with Comments';
        if (requireVilla) {
            validateVillaPictureUploaded(ensuredVillaDoc != null ? ensuredVillaDoc.Id : null);
        }

        if (ensuredVillaDoc != null && !String.isBlank(ensuredVillaDoc.AttachmentID__c)) {
            linkVillaPictureToVsr(vsr.Id, ensuredVillaDoc.Id);
        }

        Set<Id> uiExistingIds = new Set<Id>();
        for (Object o : rows) {
            if (!(o instanceof Map<String, Object>)) continue;
            Map<String, Object> row = (Map<String, Object>) o;

            Object existingIdVal = row.get('violationRecordId');
            if (existingIdVal != null && String.valueOf(existingIdVal).trim() != '') {
                try { uiExistingIds.add((Id) String.valueOf(existingIdVal)); } catch (Exception ignore) {}
            }
        }

        Map<Id, Violation__c> fixedMap = uiExistingIds.isEmpty()
            ? new Map<Id, Violation__c>()
            : new Map<Id, Violation__c>([
                SELECT Id, Is_Fixed__c
                FROM Violation__c
                WHERE Id IN :uiExistingIds
            ]);

        Set<Id> fixedIds = new Set<Id>();
        for (Violation__c v : fixedMap.values()) if (v.Is_Fixed__c == true) fixedIds.add(v.Id);

        for (Id delId : deletedViolationIds) {
            if (fixedIds.contains(delId)) {
                throw new AuraHandledException('Fixed violations cannot be deleted.');
            }
        }

        List<Violation__c> dbChildren = [
            SELECT Id, Is_Fixed__c
            FROM Violation__c
            WHERE VSR__c = :vsr.Id
        ];

        List<Violation__c> toDelete = new List<Violation__c>();
        for (Violation__c v : dbChildren) {
            if (!uiExistingIds.contains(v.Id)) {
                if (v.Is_Fixed__c == true) {
                    throw new AuraHandledException('Fixed violations cannot be deleted.');
                }
                toDelete.add(v);
            }
        }

        Set<Id> violationIdsToDelete = new Set<Id>();
        for (Violation__c v : toDelete) violationIdsToDelete.add(v.Id);
        violationIdsToDelete.addAll(deletedViolationIds);

        deleteDocumentsAndFilesByViolationIds(violationIdsToDelete);

        if (!violationIdsToDelete.isEmpty()) {
            delete [SELECT Id FROM Violation__c WHERE Id IN :violationIdsToDelete];
        }

        deleteDocumentsAndFilesByDocumentIds(deletedDocumentIds);

        List<Violation__c> toUpdate = new List<Violation__c>();

        for (Object o : rows) {
            if (!(o instanceof Map<String, Object>)) continue;
            Map<String, Object> row = (Map<String, Object>) o;

            Object existingIdVal = row.get('violationRecordId');
            if (existingIdVal == null || String.valueOf(existingIdVal).trim() == '') continue;

            Id vid;
            try { vid = (Id) String.valueOf(existingIdVal); } catch (Exception ignore) { continue; }

            if (fixedIds.contains(vid)) continue;

            Boolean isCarriedForward = hasParentCase ? readIsCarriedForward(row, hasParentCase) : false;
            Boolean isFixed = readBool(row, 'isFixed');

            if (hasParentCase && !isCarriedForward) isFixed = false;

            toUpdate.add(new Violation__c(
                Id = vid,
                Category__c = (String) row.get('category'),
                Description__c = (String) row.get('description'),
                Is_Fixed__c = isFixed
            ));
        }

        if (!toUpdate.isEmpty()) {
            if (!Schema.sObjectType.Violation__c.isUpdateable()) {
                throw new AuraHandledException('You do not have permission to update Violation records.');
            }
            update toUpdate;
        }

        if (!rows.isEmpty()) {
            if (!Schema.sObjectType.Violation__c.isCreateable()) {
                throw new AuraHandledException('You do not have permission to create Violation records.');
            }

            Id violationRtId;
            List<RecordType> rts = [
                SELECT Id, DeveloperName
                FROM RecordType
                WHERE SObjectType = 'Violation__c'
                ORDER BY IsActive DESC, DeveloperName
            ];
            for (RecordType rt : rts) {
                if (rt.DeveloperName == 'VSR') { violationRtId = rt.Id; break; }
            }
            if (violationRtId == null && !rts.isEmpty()) violationRtId = rts[0].Id;
            if (violationRtId == null) throw new AuraHandledException('No active RecordType found for Violation__c.');

            List<Violation__c> toInsert = new List<Violation__c>();

            List<Id> docIdsForNewViolations = new List<Id>();

            List<Boolean> isCarriedForwardFlags = new List<Boolean>();
            List<Id> afterDocIdsForNewViolations = new List<Id>();
            List<Id> beforeDocIdsForNewViolations = new List<Id>();
            List<String> beforeCdIdsForNewViolations = new List<String>();
            List<String> beforeNamesForNewViolations = new List<String>();

            for (Object o : rows) {
                if (!(o instanceof Map<String, Object>)) continue;
                Map<String, Object> row = (Map<String, Object>) o;

                Object existingIdVal = row.get('violationRecordId');
                if (existingIdVal != null && String.valueOf(existingIdVal).trim() != '') continue;

                Violation__c v = new Violation__c(
                    RecordTypeId = violationRtId,
                    VSR__c = vsr.Id,
                    Heading__c = (String) row.get('name'),
                    Category__c = (String) row.get('category'),
                    Description__c = (String) row.get('description')
                );

                Boolean isFixed = readBool(row, 'isFixed');

                if (isParentPayload) {
                    Boolean isCarriedForward = readIsCarriedForward(row, hasParentCase);
                    isCarriedForwardFlags.add(isCarriedForward);

                    if (!isCarriedForward) isFixed = false;
                }

                v.Is_Fixed__c = isFixed;
                toInsert.add(v);

                if (isParentPayload) {
                    afterDocIdsForNewViolations.add(
                        row.get('afterDocumentId') == null ? null : (Id) String.valueOf(row.get('afterDocumentId'))
                    );

                    beforeDocIdsForNewViolations.add(
                        row.get('beforeDocumentId') == null ? null : (Id) String.valueOf(row.get('beforeDocumentId'))
                    );

                    beforeCdIdsForNewViolations.add((String) row.get('beforeContentDocumentId'));
                    beforeNamesForNewViolations.add((String) row.get('beforeFileName'));
                } else {
                    Id docId;
                    Object docVal = row.get('documentId');
                    if (docVal != null && String.valueOf(docVal).trim() != '') docId = (Id) String.valueOf(docVal);
                    docIdsForNewViolations.add(docId);
                }
            }

            if (!toInsert.isEmpty()) {
                insert toInsert;

                if (isParentPayload) {
                    List<Documents__c> beforeDocsToInsert = new List<Documents__c>();
                    List<Documents__c> afterDocsToUpdate = new List<Documents__c>();
                    List<Documents__c> beforeDocsToUpdate = new List<Documents__c>();

                    for (Integer i = 0; i < toInsert.size(); i++) {
                        Violation__c newV = toInsert[i];

                        Boolean isCarriedForward = (i < isCarriedForwardFlags.size()) ? isCarriedForwardFlags[i] : false;

                        Id afterDocId = afterDocIdsForNewViolations[i];
                        String beforeCdId = beforeCdIdsForNewViolations[i];
                        String beforeName = beforeNamesForNewViolations[i];

                        if (isCarriedForward) {
                            if (!String.isBlank(beforeCdId)) {
                                String baseName = String.isBlank(beforeName) ? 'Before photo' : beforeName;
                                beforeDocsToInsert.add(new Documents__c(
                                    Name = buildDocumentDisplayName(baseName, 'VSR Violation Before Evidence'),
                                    Document_Type__c = 'VSR Violation Before Evidence',
                                    Status__c = 'Submitted',
                                    Is_Customer_Doc__c = true,
                                    Mandatory__c = true,
                                    Violation__c = newV.Id,
                                    AttachmentID__c = beforeCdId
                                ));
                            }

                            if (afterDocId != null) {
                                afterDocsToUpdate.add(new Documents__c(
                                    Id = afterDocId,
                                    Violation__c = newV.Id,
                                    Document_Type__c = 'VSR Violation After Evidence'
                                ));
                            }
                        } else {
                            Id beforeDocId = beforeDocIdsForNewViolations[i];
                            if (beforeDocId != null) {
                                beforeDocsToUpdate.add(new Documents__c(
                                    Id = beforeDocId,
                                    Violation__c = newV.Id,
                                    Document_Type__c = 'VSR Violation Before Evidence'
                                ));
                            }
                        }
                    }

                    if (!beforeDocsToInsert.isEmpty()) {
                        insert beforeDocsToInsert;
                        for (Documents__c d : beforeDocsToInsert) {
                            if (String.isBlank(d.AttachmentID__c)) continue;
                            safeEnsureLink(d.Id, (Id) d.AttachmentID__c);
                        }
                    }

                    if (!afterDocsToUpdate.isEmpty()) update afterDocsToUpdate;
                    if (!beforeDocsToUpdate.isEmpty()) update beforeDocsToUpdate;

                } else {
                    List<Documents__c> docsToUpdate = new List<Documents__c>();
                    for (Integer i = 0; i < toInsert.size(); i++) {
                        if (docIdsForNewViolations[i] == null) continue;
                        docsToUpdate.add(new Documents__c(
                            Id = docIdsForNewViolations[i],
                            Violation__c = toInsert[i].Id
                        ));
                    }
                    if (!docsToUpdate.isEmpty()) update docsToUpdate;
                }
            }
        }

        return vsr.Id;
    }

    @AuraEnabled
    public static Id createViolationDocument(Id caseId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');

        Documents__c doc = new Documents__c();
        doc.Name = 'VSR Evidence';
        doc.Document_Type__c = 'VSR Violation Before Evidence';
        doc.Status__c = 'Submitted';
        doc.Is_Customer_Doc__c = true;
        doc.Mandatory__c = true;
        insert doc;
        return doc.Id;
    }

    @AuraEnabled
    public static Id updateViolationDocumentAfterUpload(Id documentId, String fileName, String contentDocumentId) {
        if (documentId == null) throw new AuraHandledException('Document Id is required.');
        if (String.isBlank(contentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');

        Id newCdId = (Id) contentDocumentId;

        try {
            Documents__c doc = [
                SELECT Id, Name, AttachmentID__c, Violation__c, Document_Type__c
                FROM Documents__c
                WHERE Id = :documentId
                LIMIT 1
            ];

            if (doc.Violation__c != null) {
                Violation__c v = [
                    SELECT Id, Is_Fixed__c
                    FROM Violation__c
                    WHERE Id = :doc.Violation__c
                    LIMIT 1
                ];
                if (v.Is_Fixed__c == true) {
                    throw new AuraHandledException('This violation is fixed and is read-only. Upload is not allowed.');
                }
            }

            Id oldCdId = (Id) doc.AttachmentID__c;

            // Rename document when file is attached:
            // "<file name without extension> - <Document_Type__c>"
            // NOTE: preserve special key-based placeholder Names that are queried by Name (do NOT rename):
            // - VILLA_PIC|<caseId>
            // - VSR_AFTER|<caseId>|<sourceViolationId>
            Boolean preserveKeyName = !String.isBlank(doc.Name)
                && (doc.Name.startsWith('VILLA_PIC|') || doc.Name.startsWith('VSR_AFTER|'));

            String newName = preserveKeyName
                ? doc.Name
                : buildDocumentDisplayName(fileName, doc.Document_Type__c);

            update new Documents__c(
                Id = doc.Id,
                Name = newName,
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true,
                AttachmentID__c = contentDocumentId
            );

            safeEnsureLink(doc.Id, newCdId);
            removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, newCdId);

            if (oldCdId != null && oldCdId != newCdId) {
                safeDeleteFileHard(oldCdId);
            }

            return doc.Id;

        } catch (Exception e) {
            throw new AuraHandledException('updateViolationDocumentAfterUpload failed: ' + e.getMessage());
        }
    }

    @AuraEnabled
    public static Id createVillaPictureDocument(Id caseId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');

        String uniqueName = 'VILLA_PIC|' + String.valueOf(caseId);

        List<Documents__c> existing = [
            SELECT Id
            FROM Documents__c
            WHERE Name = :uniqueName
            AND Document_Type__c = 'Villa Picture'
            LIMIT 1
        ];
        if (!existing.isEmpty()) return existing[0].Id;

        Documents__c doc = new Documents__c(
            Name = uniqueName,
            Document_Type__c = 'Villa Picture',
            Status__c = 'Submitted',
            Is_Customer_Doc__c = true,
            Mandatory__c = true
        );
        insert doc;
        return doc.Id;
    }

    // Create (if needed) and attach villa picture to current VSR
    @AuraEnabled
    public static Id upsertVillaAndAttach(Id caseId, Id vsrId, String fileName, String contentDocumentId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');
        if (String.isBlank(contentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');

        Id docId = createVillaPictureDocument(caseId);
        update new Documents__c(Id = docId, VSR__c = vsrId);
        updateVillaPictureAfterUpload(docId, fileName, contentDocumentId);
        return docId;
    }

    // Create Violation and attach a single document (Evidence/Before) on first upload
    @AuraEnabled
    public static Map<String, Object> createViolationAndAttachDocument(
        Id caseId,
        Id vsrId,
        String heading,
        String category,
        String description,
        String documentType,
        String fileName,
        String contentDocumentId
    ) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');
        if (String.isBlank(heading)) throw new AuraHandledException('Violation heading is required.');
        if (String.isBlank(contentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');
        if (String.isBlank(documentType)) throw new AuraHandledException('Document type is required.');

        // VSR must belong to Case
        VSR__c vsr = [
            SELECT Id, Case__c
            FROM VSR__c
            WHERE Id = :vsrId
            LIMIT 1
        ];
        if (vsr.Case__c != caseId) throw new AuraHandledException('Invalid VSR for this Case.');

        // RecordType resolution (VSR)
        Id violationRtId;
        for (RecordType rt : [
            SELECT Id, DeveloperName
            FROM RecordType
            WHERE SObjectType = 'Violation__c'
            ORDER BY IsActive DESC, DeveloperName
        ]) {
            if (rt.DeveloperName == 'VSR') { violationRtId = rt.Id; break; }
            if (violationRtId == null) violationRtId = rt.Id;
        }
        if (violationRtId == null) throw new AuraHandledException('No active RecordType found for Violation__c.');

        if (!Schema.sObjectType.Violation__c.isCreateable()) {
            throw new AuraHandledException('You do not have permission to create Violation records.');
        }
        Violation__c vio = new Violation__c(
            RecordTypeId = violationRtId,
            VSR__c = vsrId,
            Heading__c = heading,
            Category__c = category,
            Description__c = description,
            Is_Fixed__c = false
        );
        insert vio;

        if (!Schema.sObjectType.Documents__c.isCreateable()) {
            throw new AuraHandledException('You do not have permission to create Documents records.');
        }
        Documents__c doc = new Documents__c(
            Name = 'VSR Evidence',
            Document_Type__c = documentType,
            Status__c = 'Submitted',
            Is_Customer_Doc__c = true,
            Mandatory__c = true,
            Violation__c = vio.Id
        );
        insert doc;

        // Attach + rename + link enforcement
        updateViolationDocumentAfterUpload(doc.Id, fileName, contentDocumentId);

        return new Map<String, Object>{
            'violationId' => vio.Id,
            'documentId' => doc.Id
        };
    }

    // Create carried-forward violation on first AFTER upload:
    // - creates child violation under current VSR
    // - creates BEFORE doc referencing parent's ContentDocument
    // - creates AFTER doc with the uploaded ContentDocument
    @AuraEnabled
    public static Map<String, Object> createCarriedForwardViolationAndAttachAfter(
        Id caseId,
        Id vsrId,
        Id sourceViolationId,
        String heading,
        String category,
        String description,
        String beforeContentDocumentId,
        String beforeFileName,
        String afterFileName,
        String afterContentDocumentId
    ) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');
        if (sourceViolationId == null) throw new AuraHandledException('Source violation id is required.');
        if (String.isBlank(afterContentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');

        VSR__c vsr = [
            SELECT Id, Case__c
            FROM VSR__c
            WHERE Id = :vsrId
            LIMIT 1
        ];
        if (vsr.Case__c != caseId) throw new AuraHandledException('Invalid VSR for this Case.');

        // Create child violation
        Id violationRtId;
        for (RecordType rt : [
            SELECT Id, DeveloperName
            FROM RecordType
            WHERE SObjectType = 'Violation__c'
            ORDER BY IsActive DESC, DeveloperName
        ]) {
            if (rt.DeveloperName == 'VSR') { violationRtId = rt.Id; break; }
            if (violationRtId == null) violationRtId = rt.Id;
        }
        if (violationRtId == null) throw new AuraHandledException('No active RecordType found for Violation__c.');

        Violation__c child = new Violation__c(
            RecordTypeId = violationRtId,
            VSR__c = vsrId,
            Heading__c = heading,
            Category__c = category,
            Description__c = description,
            Is_Fixed__c = false
        );
        insert child;

        // BEFORE doc referencing parent ContentDocument
        Documents__c beforeDoc = null;
        if (!String.isBlank(beforeContentDocumentId)) {
            beforeDoc = new Documents__c(
                Name = String.isBlank(beforeFileName) ? 'Before photo' : beforeFileName,
                Document_Type__c = 'VSR Violation Before Evidence',
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true,
                Violation__c = child.Id,
                AttachmentID__c = beforeContentDocumentId
            );
            insert beforeDoc;
            // Ensure file linked to doc
            try { safeEnsureLink(beforeDoc.Id, (Id) beforeContentDocumentId); } catch (Exception ignore) {}
        }

        // AFTER doc with uploaded file
        Documents__c afterDoc = new Documents__c(
            Name = 'After photo',
            Document_Type__c = 'VSR Violation After Evidence',
            Status__c = 'Submitted',
            Is_Customer_Doc__c = true,
            Mandatory__c = true,
            Violation__c = child.Id
        );
        insert afterDoc;
        updateViolationDocumentAfterUpload(afterDoc.Id, afterFileName, afterContentDocumentId);

        return new Map<String, Object>{
            'violationId' => child.Id,
            'afterDocumentId' => afterDoc.Id,
            'beforeDocumentId' => (beforeDoc != null ? beforeDoc.Id : null)
        };
    }

    // Immediate delete when user deletes a row (draft-safe)
    @AuraEnabled
    public static void deleteViolationAndFiles(Id violationId) {
        if (violationId == null) throw new AuraHandledException('Violation Id is required.');

        Violation__c v = [
            SELECT Id, Is_Fixed__c
            FROM Violation__c
            WHERE Id = :violationId
            LIMIT 1
        ];
        if (v.Is_Fixed__c == true) throw new AuraHandledException('Fixed violations cannot be deleted.');

        deleteDocumentsAndFilesByViolationIds(new Set<Id>{ violationId });
        delete v;
    }

    @AuraEnabled
    public static Id updateVillaPictureAfterUpload(Id documentId, String fileName, String contentDocumentId) {
        if (documentId == null) throw new AuraHandledException('Document Id is required.');
        if (String.isBlank(contentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');

        Id newCdId = (Id) contentDocumentId;

        try {
            Documents__c doc = [
                SELECT Id, Name, AttachmentID__c, VSR__c, Document_Type__c
                FROM Documents__c
                WHERE Id = :documentId
                LIMIT 1
            ];

            Id oldCdId = (Id) doc.AttachmentID__c;

            update new Documents__c(
                Id = doc.Id,
                Document_Type__c = 'Villa Picture',
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true,
                AttachmentID__c = contentDocumentId
            );

            ensureLink(doc.Id, newCdId);

            if (doc.VSR__c != null) {
                ensureLink(doc.VSR__c, newCdId);
                removeOtherFilesFromEntities(new Set<Id>{ doc.Id, doc.VSR__c }, newCdId);
            } else {
                removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, newCdId);
            }

            if (oldCdId != null && oldCdId != newCdId) {
                safeDeleteFileHard(oldCdId);
            }

            return doc.Id;

        } catch (Exception e) {
            throw new AuraHandledException('updateVillaPictureAfterUpload failed: ' + e.getMessage());
        }
    }

    @AuraEnabled
    public static Map<Id, Id> getOrCreateAfterPhotoDocuments(Id caseId, List<Id> sourceViolationIds) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (sourceViolationIds == null || sourceViolationIds.isEmpty()) return new Map<Id, Id>();

        Map<Id, Id> result = new Map<Id, Id>();

        Set<String> keys = new Set<String>();
        Map<String, Id> keyToViolation = new Map<String, Id>();

        for (Id vId : sourceViolationIds) {
            String k = 'VSR_AFTER|' + String.valueOf(caseId) + '|' + String.valueOf(vId);
            keys.add(k);
            keyToViolation.put(k, vId);
        }

        for (Documents__c d : [
            SELECT Id, Name
            FROM Documents__c
            WHERE Name IN :keys
            AND Document_Type__c = 'VSR Violation After Evidence'
        ]) {
            Id vId = keyToViolation.get(d.Name);
            if (vId != null) result.put(vId, d.Id);
        }

        List<Documents__c> toInsert = new List<Documents__c>();
        for (String k : keys) {
            Id vId = keyToViolation.get(k);
            if (vId == null) continue;
            if (result.containsKey(vId)) continue;

            toInsert.add(new Documents__c(
                Name = k,
                Document_Type__c = 'VSR Violation After Evidence',
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true
            ));
        }

        if (!toInsert.isEmpty()) {
            insert toInsert;
            for (Documents__c d : toInsert) {
                Id vId = keyToViolation.get(d.Name);
                if (vId != null) result.put(vId, d.Id);
            }
        }

        return result;
    }

    @AuraEnabled(cacheable=true)
    public static List<Documents__c> getDocumentsInfo(List<Id> documentIds) {
        if (documentIds == null || documentIds.isEmpty()) return new List<Documents__c>();
        return [
            SELECT Id, Name, AttachmentID__c, Document_Type__c
            FROM Documents__c
            WHERE Id IN :documentIds
        ];
    }

    @AuraEnabled(cacheable=true)
    public static Map<Id, String> getThumbnailUrls(List<Id> contentDocumentIds) {
        Map<Id, String> outMap = new Map<Id, String>();
        if (contentDocumentIds == null || contentDocumentIds.isEmpty()) return outMap;

        for (ContentVersion cv : [
            SELECT Id, ContentDocumentId, FileType
            FROM ContentVersion
            WHERE ContentDocumentId IN :contentDocumentIds
            AND IsLatest = true
        ]) {
            String ft = (cv.FileType == null) ? '' : cv.FileType.toLowerCase();
            Boolean isImage = (ft == 'png' || ft == 'jpg' || ft == 'jpeg');

            if (isImage) {
                String url = '/sfc/servlet.shepherd/version/renditionDownload'
                    + '?rendition=THUMB240BY180'
                    + '&versionId=' + cv.Id;
                outMap.put(cv.ContentDocumentId, url);
            }
        }
        return outMap;
    }

    private static void validateRowsHaveRequiredFiles(List<Object> rows, Boolean hasParentCase) {
        Set<Id> docIdsToCheck = new Set<Id>();

        for (Object o : rows) {
            if (!(o instanceof Map<String, Object>)) continue;
            Map<String, Object> r = (Map<String, Object>) o;

            Boolean isFixedOnLoad = readBool(r, 'isFixedOnLoad');

            if (hasParentCase) {
                Boolean isCarriedForward = readIsCarriedForward(r, hasParentCase);

                if (isCarriedForward) {
                    if (!isFixedOnLoad) {
                        Object afterDocVal = r.get('afterDocumentId');
                        if (afterDocVal == null || String.valueOf(afterDocVal).trim() == '') {
                            throw new AuraHandledException('Please upload an after photo for every carried-forward violation before submitting.');
                        }
                        docIdsToCheck.add((Id) String.valueOf(afterDocVal));
                    }
                } else {
                    Object beforeDocVal = r.get('beforeDocumentId');
                    if (beforeDocVal == null || String.valueOf(beforeDocVal).trim() == '') {
                        throw new AuraHandledException('Please upload a before photo for every new violation before submitting.');
                    }
                    docIdsToCheck.add((Id) String.valueOf(beforeDocVal));
                }
            } else {
                Object docVal = r.get('documentId');
                if (docVal == null || String.valueOf(docVal).trim() == '') {
                    throw new AuraHandledException('Please upload a document for every violation before submitting.');
                }
                docIdsToCheck.add((Id) String.valueOf(docVal));
            }
        }

        if (docIdsToCheck.isEmpty()) return;

        Map<Id, Documents__c> docs = new Map<Id, Documents__c>([
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Id IN :docIdsToCheck
        ]);

        for (Id dId : docIdsToCheck) {
            Documents__c d = docs.get(dId);
            if (d == null || String.isBlank(d.AttachmentID__c)) {
                throw new AuraHandledException('Please upload the required photo/document for each violation before submitting.');
            }
        }
    }

    private static void validateVillaPictureUploaded(Id villaDocumentId) {
        if (villaDocumentId == null) {
            throw new AuraHandledException(
                'Villa picture is required when the VSR status is Clear or Clear with Comments (or when there are no violations).'
            );
        }

        Documents__c d = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Id = :villaDocumentId
            LIMIT 1
        ];

        if (d == null || String.isBlank(d.AttachmentID__c)) {
            throw new AuraHandledException(
                'Villa picture is required when the VSR status is Clear or Clear with Comments (or when there are no violations).'
            );
        }
    }

    private static void linkVillaPictureToVsr(Id vsrId, Id villaDocumentId) {
        if (vsrId == null || villaDocumentId == null) return;

        Documents__c d = [
            SELECT Id, AttachmentID__c, VSR__c
            FROM Documents__c
            WHERE Id = :villaDocumentId
            LIMIT 1
        ];

        if (d.VSR__c != vsrId) {
            d.VSR__c = vsrId;
            update d;
        }

        if (String.isBlank(d.AttachmentID__c)) return;

        Integer existing = [
            SELECT COUNT()
            FROM ContentDocumentLink
            WHERE LinkedEntityId = :vsrId
            AND ContentDocumentId = :d.AttachmentID__c
        ];

        if (existing == 0) {
            insert new ContentDocumentLink(
                LinkedEntityId = vsrId,
                ContentDocumentId = (Id) d.AttachmentID__c,
                ShareType = 'V',
                Visibility = 'AllUsers'
            );
        }
    }

    private static void deleteDocumentsAndFilesByViolationIds(Set<Id> violationIds) {
        if (violationIds == null || violationIds.isEmpty()) return;

        List<Documents__c> docs = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Violation__c IN :violationIds
        ];

        Set<Id> contentDocIds = new Set<Id>();
        for (Documents__c d : docs) {
            if (!String.isBlank(d.AttachmentID__c)) {
                try { contentDocIds.add((Id) d.AttachmentID__c); } catch (Exception ignore) {}
            }
        }

        if (!docs.isEmpty()) delete docs;
        for (Id cdId : contentDocIds) safeDeleteFileHard(cdId);
    }

    private static void deleteDocumentsAndFilesByDocumentIds(Set<Id> documentIds) {
        if (documentIds == null || documentIds.isEmpty()) return;

        List<Documents__c> docs = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Id IN :documentIds
        ];

        Set<Id> contentDocIds = new Set<Id>();
        for (Documents__c d : docs) {
            if (!String.isBlank(d.AttachmentID__c)) {
                try { contentDocIds.add((Id) d.AttachmentID__c); } catch (Exception ignore) {}
            }
        }

        if (!docs.isEmpty()) delete docs;
        for (Id cdId : contentDocIds) safeDeleteFileHard(cdId);
    }

    private static void ensureLink(Id entityId, Id contentDocumentId) {
        if (entityId == null || contentDocumentId == null) return;

        Integer existing = [
            SELECT COUNT()
            FROM ContentDocumentLink
            WHERE LinkedEntityId = :entityId
            AND ContentDocumentId = :contentDocumentId
        ];

        if (existing == 0) {
            insert new ContentDocumentLink(
                LinkedEntityId = entityId,
                ContentDocumentId = contentDocumentId,
                ShareType = 'V',
                Visibility = 'AllUsers'
            );
        }
    }

    private static void safeEnsureLink(Id entityId, Id contentDocumentId) {
        if (entityId == null || contentDocumentId == null) return;
        try {
            ensureLink(entityId, contentDocumentId);
        } catch (Exception ignore) {
            System.debug('safeEnsureLink failed: ' + ignore.getMessage());
        }
    }

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

    private static Boolean readBool(Map<String, Object> row, String key) {
        if (row == null || String.isBlank(key) || !row.containsKey(key) || row.get(key) == null) return false;
        Object v = row.get(key);
        if (v instanceof Boolean) return (Boolean) v;
        return String.valueOf(v).toLowerCase() == 'true';
    }

    private static Boolean readIsCarriedForward(Map<String, Object> row, Boolean hasParentCase) {
        if (!hasParentCase) return false;

        if (row != null && row.containsKey('isCarriedForward') && row.get('isCarriedForward') != null) {
            Object v = row.get('isCarriedForward');
            if (v instanceof Boolean) return (Boolean) v;
            return String.valueOf(v).toLowerCase() == 'true';
        }

        String beforeCd = (String) row.get('beforeContentDocumentId');
        Object srcVal = row.get('sourceViolationRecordId');
        Boolean hasBeforeBaseline = !String.isBlank(beforeCd);
        Boolean hasSource = (srcVal != null && String.valueOf(srcVal).trim() != '');
        return hasBeforeBaseline || hasSource;
    }

    /**
     * Documents__c.Name = "<file name without extension> - <Document Type>"
     * (No extension; includes Document_Type__c for clarity.)
     */
    private static String buildDocumentDisplayName(String fileName, String documentType) {
        String base = stripExtension(fileName);
        if (String.isBlank(base)) base = 'File';

        String dt = String.isBlank(documentType) ? '' : documentType.trim();
        if (String.isBlank(dt)) return base;

        return base + ' - ' + dt;
    }

    private static String stripExtension(String fileName) {
        if (String.isBlank(fileName)) return null;
        String f = fileName.trim();
        Integer dot = f.lastIndexOf('.');
        if (dot <= 0 || dot == f.length() - 1) return f;
        return f.substring(0, dot);
    }

    private static void removeOtherFilesFromEntities(Set<Id> linkedEntityIds, Id keepContentDocumentId) {
        if (linkedEntityIds == null || linkedEntityIds.isEmpty() || keepContentDocumentId == null) return;

        List<ContentDocumentLink> extraLinks = [
            SELECT Id, ContentDocumentId, LinkedEntityId
            FROM ContentDocumentLink
            WHERE LinkedEntityId IN :linkedEntityIds
            AND ContentDocumentId != :keepContentDocumentId
        ];

        if (extraLinks.isEmpty()) return;

        Set<Id> extraContentDocIds = new Set<Id>();
        for (ContentDocumentLink l : extraLinks) extraContentDocIds.add(l.ContentDocumentId);

        try {
            Database.delete(extraLinks, false);
        } catch (Exception e) {
            System.debug('removeOtherFilesFromEntities: link delete failed: ' + e.getMessage());
        }

        for (Id cdId : extraContentDocIds) {
            try {
                Integer remaining = [
                    SELECT COUNT()
                    FROM ContentDocumentLink
                    WHERE ContentDocumentId = :cdId
                ];
                if (remaining == 0) {
                    Database.delete(new List<ContentDocument>{ new ContentDocument(Id = cdId) }, false);
                }
            } catch (Exception e) {
                System.debug('removeOtherFilesFromEntities: content doc delete failed: ' + e.getMessage());
            }
        }
    }

    private static Documents__c ensureVillaPictureForSubmit(Id caseId, Id vsrId, Id passedVillaDocumentId) {
        if (caseId == null) return null;

        Documents__c doc;

        if (passedVillaDocumentId != null) {
            doc = [
                SELECT Id, Name, AttachmentID__c, VSR__c, Document_Type__c
                FROM Documents__c
                WHERE Id = :passedVillaDocumentId
                LIMIT 1
            ];
        } else {
            doc = getVillaDocForCase(caseId);
        }

        if (doc == null) return null;

        if (vsrId != null && doc.VSR__c != vsrId) {
            doc.VSR__c = vsrId;
            doc.Document_Type__c = 'Villa Picture';
            update doc;
        }

        // NO carry-forward of villa picture. Only enforce links if the current-case doc has a file.
        if (!String.isBlank(doc.AttachmentID__c)) {
            Id cdId;
            try { cdId = (Id) doc.AttachmentID__c; } catch (Exception ignore) { cdId = null; }

            if (cdId != null) {
                ensureLink(doc.Id, cdId);
                if (vsrId != null) ensureLink(vsrId, cdId);

                if (vsrId != null) removeOtherFilesFromEntities(new Set<Id>{ doc.Id, vsrId }, cdId);
                else removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, cdId);
            }
        }

        return doc;
    }

    private static Documents__c getVillaDocForCase(Id caseId) {
        if (caseId == null) return null;
        String uniqueName = 'VILLA_PIC|' + String.valueOf(caseId);
        List<Documents__c> docs = [
            SELECT Id, Name, AttachmentID__c, VSR__c, Document_Type__c, CreatedDate
            FROM Documents__c
            WHERE Document_Type__c = 'Villa Picture'
            AND Name = :uniqueName
            ORDER BY CreatedDate DESC
            LIMIT 1
        ];
        return docs.isEmpty() ? null : docs[0];
    }

    private static Id getParentVillaContentDocumentId(Id parentCaseId, Id parentVsrId) {
        if (parentCaseId == null) return null;

        String uniqueName = 'VILLA_PIC|' + String.valueOf(parentCaseId);

        List<Documents__c> docs = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Document_Type__c = 'Villa Picture'
            AND Name = :uniqueName
            ORDER BY CreatedDate DESC
            LIMIT 1
        ];
        if (!docs.isEmpty()) {
            try { return (Id) docs[0].AttachmentID__c; } catch (Exception ignore) {}
        }

        if (parentVsrId != null) {
            List<Documents__c> byVsr = [
                SELECT Id, AttachmentID__c
                FROM Documents__c
                WHERE Document_Type__c = 'Villa Picture'
                AND VSR__c = :parentVsrId
                ORDER BY CreatedDate DESC
                LIMIT 1
            ];
            if (!byVsr.isEmpty()) {
                try { return (Id) byVsr[0].AttachmentID__c; } catch (Exception ignore) {}
            }
        }

        List<Documents__c> anyLatest = [
            SELECT Id, AttachmentID__c
            FROM Documents__c
            WHERE Document_Type__c = 'Villa Picture'
            ORDER BY CreatedDate DESC
            LIMIT 1
        ];
        if (!anyLatest.isEmpty()) {
            try { return (Id) anyLatest[0].AttachmentID__c; } catch (Exception ignore) {}
        }

        return null;
    }

    private static String getFileTitleSafe(Id contentDocumentId) {
        if (contentDocumentId == null) return null;

        try {
            ContentDocument cd = [
                SELECT Id, Title
                FROM ContentDocument
                WHERE Id = :contentDocumentId
                LIMIT 1
            ];
            return cd != null ? cd.Title : null;
        } catch (Exception e) {
            return null;
        }
    }

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

        if (!String.isBlank(doc.AttachmentID__c)) {
            doc.AttachmentID__c = null;
            update doc;
        }

        if (oldCdId != null) {
            safeDeleteFileHard(oldCdId);
        }
    }
}

