public with sharing class vSRViolationCaptureController {

    // ============================================================
    // Document Configuration (Custom Metadata) - VSR
    // ============================================================
    private static final String DOC_CATEGORY_VSR = 'VSR';

    // Per requirement: identify required configs by Document_Name__c
    private static final String CFG_NAME_VILLA  = 'Villa Photo';
    private static final String CFG_NAME_AFTER  = 'Violation After Photo';
    private static final String CFG_NAME_BEFORE = 'Violation Before Photo';
    private static final String CFG_NAME_NOC    = 'Violation NOC Document';

    // Cache for the current transaction (prevents repeated metadata queries)
    private static Map<String, Document_Configuration__mdt> vsrConfigByName;

    private static Map<String, Document_Configuration__mdt> getVsrConfigByName() {
        if (vsrConfigByName != null) return vsrConfigByName;
        vsrConfigByName = new Map<String, Document_Configuration__mdt>();

        List<Document_Configuration__mdt> cfgs = [
            SELECT Document_Name__c, Document_Type__c, Applicable_Resell_Types__c, Document_Category__c
            FROM Document_Configuration__mdt
            WHERE Document_Category__c = :DOC_CATEGORY_VSR
        ];

        // Repo may not include CMDT records; make tests self-contained.
        if (Test.isRunningTest() && (cfgs == null || cfgs.isEmpty())) {
            cfgs = new List<Document_Configuration__mdt>{
                new Document_Configuration__mdt(
                    Document_Name__c = CFG_NAME_VILLA,
                    Document_Type__c = 'Villa Picture',
                    Document_Category__c = DOC_CATEGORY_VSR
                ),
                new Document_Configuration__mdt(
                    Document_Name__c = CFG_NAME_BEFORE,
                    Document_Type__c = 'Violation Before Document',
                    Document_Category__c = DOC_CATEGORY_VSR
                ),
                new Document_Configuration__mdt(
                    Document_Name__c = CFG_NAME_AFTER,
                    Document_Type__c = 'Violation After Document',
                    Document_Category__c = DOC_CATEGORY_VSR
                ),
                new Document_Configuration__mdt(
                    Document_Name__c = CFG_NAME_NOC,
                    Document_Type__c = 'NOC Document',
                    Document_Category__c = DOC_CATEGORY_VSR
                )
            };
        }

        for (Document_Configuration__mdt cfg : cfgs) {
            if (!String.isBlank(cfg.Document_Name__c)) {
                vsrConfigByName.put(cfg.Document_Name__c, cfg);
            }
        }
        return vsrConfigByName;
    }

    private static Document_Configuration__mdt requireVsrCfg(String documentName) {
        Document_Configuration__mdt cfg = getVsrConfigByName().get(documentName);
        if (cfg == null || String.isBlank(cfg.Document_Type__c)) {
            throw new AuraHandledException('Missing Document Configuration for VSR: ' + documentName);
        }
        return cfg;
    }

    private static String configuredDocNameForType(String documentType, String fallback) {
        if (String.isBlank(documentType)) return fallback;
        for (Document_Configuration__mdt cfg : getVsrConfigByName().values()) {
            if (cfg != null && cfg.Document_Type__c == documentType && !String.isBlank(cfg.Document_Name__c)) {
                return cfg.Document_Name__c;
            }
        }
        return fallback;
    }

    private static Map<String, String> getRequiredVsrTypes() {
        // returns a small lookup of logical key -> Document_Type__c from metadata
        Map<String, String> out = new Map<String, String>();
        out.put('VILLA',  requireVsrCfg(CFG_NAME_VILLA).Document_Type__c);
        out.put('BEFORE', requireVsrCfg(CFG_NAME_BEFORE).Document_Type__c);
        out.put('AFTER',  requireVsrCfg(CFG_NAME_AFTER).Document_Type__c);
        out.put('NOC',    requireVsrCfg(CFG_NAME_NOC).Document_Type__c);
        return out;
    }

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
        if (!existing.isEmpty()) {
            // Ensure villa placeholder exists even for previously-created VSRs
            ensureVillaDocumentForVsr(existing[0].Id);
            // Follow-up behavior: ensure parent violations are copied into this VSR once (draft-safe)
            ensureParentViolationsCopiedIntoChildVsr(caseId, existing[0].Id);
            return existing[0].Id;
        }

        Case caseRec = [
            SELECT Id, Opportunity__c, Related_Case__c
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
        // Create villa placeholder as part of VSR creation
        ensureVillaDocumentForVsr(vsr.Id);
        // Follow-up behavior: ensure parent violations are copied into this VSR once (draft-safe)
        ensureParentViolationsCopiedIntoChildVsr(caseId, vsr.Id);
        return vsr.Id;
    }

    /**
     * Follow-up case requirement:
     * When a Case has Related_Case__c (parent Case), we must insert all parent violations into the
     * current Case's VSR immediately when the modal opens, so they persist even if the agent closes/reloads.
     *
     * - Does NOT set VSR status/dates.
     * - Copies the parent's BEFORE doc as the child's BEFORE doc (as an independent cloned file).
     * - Creates AFTER/NOC placeholders for each copied violation.
     */
    private static void ensureParentViolationsCopiedIntoChildVsr(Id caseId, Id childVsrId) {
        if (caseId == null || childVsrId == null) return;

        Case c = [
            SELECT Id, Related_Case__c
            FROM Case
            WHERE Id = :caseId
            LIMIT 1
        ];
        if (c.Related_Case__c == null) return;

        Integer existingChildCount = [
            SELECT COUNT()
            FROM Violation__c
            WHERE VSR__c = :childVsrId
        ];
        if (existingChildCount > 0) return; // already copied/created

        List<VSR__c> parentVsrs = [
            SELECT Id
            FROM VSR__c
            WHERE Case__c = :c.Related_Case__c
            LIMIT 2
        ];
        if (parentVsrs.size() > 1) {
            throw new AuraHandledException('More than one VSR exists for the parent Case. Please contact support.');
        }
        if (parentVsrs.isEmpty()) return;
        Id parentVsrId = parentVsrs[0].Id;

        List<Violation__c> parentViolations = [
            SELECT Id, Heading__c, Category__c, Description__c, Is_Fixed__c
            FROM Violation__c
            WHERE VSR__c = :parentVsrId
            ORDER BY CreatedDate ASC
        ];
        if (parentViolations.isEmpty()) return;

        // Insert child violations (carried-forward) under the current VSR.
        // New_Violation__c remains false for carried-forward rows.
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

        List<Violation__c> childToInsert = new List<Violation__c>();
        for (Violation__c pv : parentViolations) {
            childToInsert.add(new Violation__c(
                RecordTypeId = violationRtId,
                VSR__c = childVsrId,
                Heading__c = pv.Heading__c,
                Category__c = pv.Category__c,
                Description__c = pv.Description__c,
                Is_Fixed__c = pv.Is_Fixed__c,
                New_Violation__c = false
            ));
        }
        insert childToInsert;

        // Map parent -> child by index (preserves order)
        Map<Id, Id> parentToChild = new Map<Id, Id>();
        for (Integer i = 0; i < parentViolations.size(); i++) {
            parentToChild.put(parentViolations[i].Id, childToInsert[i].Id);
        }

        // Create placeholders for child violations
        Map<Id, Map<String, Id>> placeholdersByChild = ensureViolationPlaceholders(new List<Id>(parentToChild.values()));

        // Find parent's docs (by configured types)
        Map<String, String> types = getRequiredVsrTypes();
        String beforeType = types.get('BEFORE');
        String afterType  = types.get('AFTER');
        String nocType    = types.get('NOC');

        Map<Id, Documents__c> parentBeforeByViolation = new Map<Id, Documents__c>();
        Map<Id, Documents__c> parentAfterByViolation  = new Map<Id, Documents__c>();
        Map<Id, Documents__c> parentNocByViolation    = new Map<Id, Documents__c>();

        for (Documents__c d : [
            SELECT Id, Violation__c, AttachmentID__c, Document_Type__c, LastModifiedDate
            FROM Documents__c
            WHERE Violation__c IN :parentToChild.keySet()
            AND Document_Type__c IN :new List<String>{ beforeType, afterType, nocType }
            ORDER BY LastModifiedDate DESC
        ]) {
            if (d.Violation__c == null) continue;
            if (d.Document_Type__c == beforeType) {
                if (!parentBeforeByViolation.containsKey(d.Violation__c)) parentBeforeByViolation.put(d.Violation__c, d);
            } else if (d.Document_Type__c == afterType) {
                if (!parentAfterByViolation.containsKey(d.Violation__c)) parentAfterByViolation.put(d.Violation__c, d);
            } else if (d.Document_Type__c == nocType) {
                if (!parentNocByViolation.containsKey(d.Violation__c)) parentNocByViolation.put(d.Violation__c, d);
            }
        }

        // Attach cloned BEFORE/AFTER/NOC files to each child placeholder (if present on parent)
        List<Documents__c> docsToUpdate = new List<Documents__c>();
        for (Id parentVioId : parentToChild.keySet()) {
            Id childVioId = parentToChild.get(parentVioId);
            if (childVioId == null) continue;

            Id childBeforeDocId = (placeholdersByChild.containsKey(childVioId) ? placeholdersByChild.get(childVioId).get('BEFORE') : null);
            Id childAfterDocId  = (placeholdersByChild.containsKey(childVioId) ? placeholdersByChild.get(childVioId).get('AFTER') : null);
            Id childNocDocId    = (placeholdersByChild.containsKey(childVioId) ? placeholdersByChild.get(childVioId).get('NOC') : null);

            // BEFORE
            Documents__c parentBefore = parentBeforeByViolation.get(parentVioId);
            if (childBeforeDocId != null && parentBefore != null && !String.isBlank(parentBefore.AttachmentID__c)) {
                Id parentContentDocId = resolveContentDocumentIdFromAttachment(parentBefore.AttachmentID__c);
                if (parentContentDocId != null) {
                    Id newChildVersionId = cloneContentDocumentToFirstPublishLocationReturnVersionId(parentContentDocId, childBeforeDocId);
                    if (newChildVersionId != null) {
                        docsToUpdate.add(new Documents__c(
                            Id = childBeforeDocId,
                            AttachmentID__c = String.valueOf(newChildVersionId),
                            Status__c = 'Submitted',
                            Is_Customer_Doc__c = true
                        ));
                    }
                }
            }

            // AFTER
            Documents__c parentAfter = parentAfterByViolation.get(parentVioId);
            if (childAfterDocId != null && parentAfter != null && !String.isBlank(parentAfter.AttachmentID__c)) {
                Id parentContentDocId = resolveContentDocumentIdFromAttachment(parentAfter.AttachmentID__c);
                if (parentContentDocId != null) {
                    Id newChildVersionId = cloneContentDocumentToFirstPublishLocationReturnVersionId(parentContentDocId, childAfterDocId);
                    if (newChildVersionId != null) {
                        docsToUpdate.add(new Documents__c(
                            Id = childAfterDocId,
                            AttachmentID__c = String.valueOf(newChildVersionId),
                            Status__c = 'Submitted',
                            Is_Customer_Doc__c = true
                        ));
                    }
                }
            }

            // NOC
            Documents__c parentNoc = parentNocByViolation.get(parentVioId);
            if (childNocDocId != null && parentNoc != null && !String.isBlank(parentNoc.AttachmentID__c)) {
                Id parentContentDocId = resolveContentDocumentIdFromAttachment(parentNoc.AttachmentID__c);
                if (parentContentDocId != null) {
                    Id newChildVersionId = cloneContentDocumentToFirstPublishLocationReturnVersionId(parentContentDocId, childNocDocId);
                    if (newChildVersionId != null) {
                        docsToUpdate.add(new Documents__c(
                            Id = childNocDocId,
                            AttachmentID__c = String.valueOf(newChildVersionId),
                            Status__c = 'Submitted',
                            Is_Customer_Doc__c = true
                        ));
                    }
                }
            }
        }
        if (!docsToUpdate.isEmpty()) update docsToUpdate;

        // Recalculate numbering for the newly-created carried-forward set
        recalcViolationNumbers(childVsrId);
    }

    // ============================================================
    // Placeholder creation
    // ============================================================
    @AuraEnabled
    public static Id ensureVillaDocumentForVsr(Id vsrId) {
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');

        Map<String, String> types = getRequiredVsrTypes();
        String villaType = types.get('VILLA');
        String villaName = requireVsrCfg(CFG_NAME_VILLA).Document_Name__c;

        List<Documents__c> existing = [
            SELECT Id
            FROM Documents__c
            WHERE VSR__c = :vsrId
            AND Document_Type__c = :villaType
            LIMIT 1
        ];
        if (!existing.isEmpty()) return existing[0].Id;

        if (!Schema.sObjectType.Documents__c.isCreateable()) {
            throw new AuraHandledException('You do not have permission to create Documents records.');
        }
        Documents__c doc = new Documents__c(
            Name = villaName,
            Document_Type__c = villaType,
            Status__c = 'Submitted',
            Is_Customer_Doc__c = true,
            Mandatory__c = true,
            VSR__c = vsrId
        );
        insert doc;
        return doc.Id;
    }

    @AuraEnabled
    public static Map<String, Object> createViolationWithPlaceholders(
        Id caseId,
        Id vsrId,
        String heading,
        String category,
        String description,
        Boolean isNewViolation
    ) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');
        if (String.isBlank(heading)) throw new AuraHandledException('Violation heading is required.');

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
            Is_Fixed__c = false,
            New_Violation__c = (isNewViolation == true)
        );
        insert vio;

        Map<String, Id> docIds = ensureViolationPlaceholdersInternal(vio.Id);
        // Keep Violation_Number__c consistent after insert
        recalcViolationNumbers(vsrId);
        return new Map<String, Object>{
            'violationId' => vio.Id,
            'beforeDocumentId' => docIds.get('BEFORE'),
            'afterDocumentId' => docIds.get('AFTER'),
            'nocDocumentId' => docIds.get('NOC')
        };
    }

    @AuraEnabled
    public static Map<Id, Map<String, Id>> ensureViolationPlaceholders(List<Id> violationIds) {
        Map<Id, Map<String, Id>> out = new Map<Id, Map<String, Id>>();
        if (violationIds == null || violationIds.isEmpty()) return out;

        Set<Id> vids = new Set<Id>();
        for (Id vid : violationIds) if (vid != null) vids.add(vid);
        if (vids.isEmpty()) return out;

        Map<String, String> types = getRequiredVsrTypes();
        String beforeType = types.get('BEFORE');
        String afterType  = types.get('AFTER');
        String nocType    = types.get('NOC');
        List<String> allTypes = new List<String>{ beforeType, afterType, nocType };

        Map<String, String> desiredNameByType = new Map<String, String>{
            beforeType => requireVsrCfg(CFG_NAME_BEFORE).Document_Name__c,
            afterType  => requireVsrCfg(CFG_NAME_AFTER).Document_Name__c,
            nocType    => requireVsrCfg(CFG_NAME_NOC).Document_Name__c
        };

        // existing docs by (ViolationId -> (DocType -> DocId))
        Map<Id, Map<String, Id>> existingByVio = new Map<Id, Map<String, Id>>();
        for (Documents__c d : [
            SELECT Id, Violation__c, Document_Type__c
            FROM Documents__c
            WHERE Violation__c IN :vids
            AND Document_Type__c IN :allTypes
        ]) {
            if (d.Violation__c == null || String.isBlank(d.Document_Type__c)) continue;
            if (!existingByVio.containsKey(d.Violation__c)) existingByVio.put(d.Violation__c, new Map<String, Id>());
            Map<String, Id> byType = existingByVio.get(d.Violation__c);
            if (!byType.containsKey(d.Document_Type__c)) byType.put(d.Document_Type__c, d.Id);
        }

        List<Documents__c> toInsert = new List<Documents__c>();
        for (Id vid : vids) {
            Map<String, Id> byType = existingByVio.containsKey(vid) ? existingByVio.get(vid) : new Map<String, Id>();
            for (String docType : allTypes) {
                if (String.isBlank(docType)) continue;
                if (byType.containsKey(docType)) continue;
                Boolean isMandatory = (docType != nocType);
                toInsert.add(new Documents__c(
                    Name = desiredNameByType.get(docType),
                    Document_Type__c = docType,
                    Status__c = 'Submitted',
                    Is_Customer_Doc__c = true,
                    Mandatory__c = isMandatory,
                    Violation__c = vid
                ));
            }
        }

        if (!toInsert.isEmpty()) {
            if (!Schema.sObjectType.Documents__c.isCreateable()) {
                throw new AuraHandledException('You do not have permission to create Documents records.');
            }
            insert toInsert;
            for (Documents__c dIns : toInsert) {
                if (dIns.Violation__c == null || String.isBlank(dIns.Document_Type__c)) continue;
                if (!existingByVio.containsKey(dIns.Violation__c)) existingByVio.put(dIns.Violation__c, new Map<String, Id>());
                Map<String, Id> byType = existingByVio.get(dIns.Violation__c);
                if (!byType.containsKey(dIns.Document_Type__c)) byType.put(dIns.Document_Type__c, dIns.Id);
            }
        }

        for (Id vid : vids) {
            Map<String, Id> byType = existingByVio.containsKey(vid) ? existingByVio.get(vid) : new Map<String, Id>();
            out.put(vid, new Map<String, Id>{
                'BEFORE' => byType.get(beforeType),
                'AFTER' => byType.get(afterType),
                'NOC' => byType.get(nocType)
            });
        }
        return out;
    }

    private static Map<String, Id> ensureViolationPlaceholdersInternal(Id violationId) {
        if (violationId == null) return new Map<String, Id>();

        Map<String, String> types = getRequiredVsrTypes();
        String beforeType = types.get('BEFORE');
        String afterType  = types.get('AFTER');
        String nocType    = types.get('NOC');

        Map<String, String> desiredNameByType = new Map<String, String>{
            beforeType => requireVsrCfg(CFG_NAME_BEFORE).Document_Name__c,
            afterType  => requireVsrCfg(CFG_NAME_AFTER).Document_Name__c,
            nocType    => requireVsrCfg(CFG_NAME_NOC).Document_Name__c
        };

        Map<String, Id> foundByType = new Map<String, Id>();
        for (Documents__c d : [
            SELECT Id, Document_Type__c
            FROM Documents__c
            WHERE Violation__c = :violationId
            AND Document_Type__c IN :new List<String>{ beforeType, afterType, nocType }
        ]) {
            if (!String.isBlank(d.Document_Type__c) && !foundByType.containsKey(d.Document_Type__c)) {
                foundByType.put(d.Document_Type__c, d.Id);
            }
        }

        List<Documents__c> toInsert = new List<Documents__c>();
        for (String docType : new List<String>{ beforeType, afterType, nocType }) {
            if (String.isBlank(docType)) continue;
            if (foundByType.containsKey(docType)) continue;

            Boolean isMandatory = (docType != nocType);
            toInsert.add(new Documents__c(
                Name = desiredNameByType.get(docType),
                Document_Type__c = docType,
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = isMandatory,
                Violation__c = violationId
            ));
        }

        if (!toInsert.isEmpty()) {
            if (!Schema.sObjectType.Documents__c.isCreateable()) {
                throw new AuraHandledException('You do not have permission to create Documents records.');
            }
            insert toInsert;
            for (Documents__c dIns : toInsert) {
                if (!String.isBlank(dIns.Document_Type__c) && !foundByType.containsKey(dIns.Document_Type__c)) {
                    foundByType.put(dIns.Document_Type__c, dIns.Id);
                }
            }
        }

        Map<String, Id> out = new Map<String, Id>();
        out.put('BEFORE', foundByType.get(beforeType));
        out.put('AFTER', foundByType.get(afterType));
        out.put('NOC', foundByType.get(nocType));
        return out;
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
                'vsrStatus' => null,
                'villaDocumentId' => null,
                'villaFileName' => requireVsrCfg(CFG_NAME_VILLA).Document_Name__c,
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
        Map<String, String> types = getRequiredVsrTypes();
        String beforeType = types.get('BEFORE');
        String afterType  = types.get('AFTER');
        String villaType  = types.get('VILLA');

        Documents__c currentVillaDoc = getVillaDocForVsr(currentVsr.Id, villaType);

        Id currentVillaCd = null;
        if (currentVillaDoc != null && !String.isBlank(currentVillaDoc.AttachmentID__c)) {
            currentVillaCd = resolveContentDocumentIdFromAttachment(currentVillaDoc.AttachmentID__c);
        }

        Id villaCdToShow = currentVillaCd;
        String villaDisplayName = getFileTitleSafe(villaCdToShow);
        if (String.isBlank(villaDisplayName)) villaDisplayName = requireVsrCfg(CFG_NAME_VILLA).Document_Name__c;

        // Load violations:
        // - baselineMode: load parent VSR violations as read-only baseline rows (no delete/category edits in UI)
        // - else: load current VSR violations
        Id vsrToLoadId = baselineMode ? parentVsr.Id : currentVsr.Id;
        List<Violation__c> violations = [
            SELECT Id, Heading__c, Category__c, Description__c, Is_Fixed__c, New_Violation__c, Violation_Number__c
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

                if (d.Document_Type__c == beforeType) {
                    if (!bestBefore.containsKey(d.Violation__c)) bestBefore.put(d.Violation__c, d);
                } else if (d.Document_Type__c == afterType) {
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
                    'violationNumber' => v.Violation_Number__c,
                    'isFixed' => v.Is_Fixed__c,
                    'isFixedOnLoad' => v.Is_Fixed__c,
                    'documentId' => (docPick != null ? docPick.Id : null),
                    'contentDocumentId' => (docPick != null && resolveContentDocumentIdFromAttachment(docPick.AttachmentID__c) != null
                        ? String.valueOf(resolveContentDocumentIdFromAttachment(docPick.AttachmentID__c))
                        : null),
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
                    'beforeContentDocumentId' => (beforePick != null && resolveContentDocumentIdFromAttachment(beforePick.AttachmentID__c) != null
                        ? String.valueOf(resolveContentDocumentIdFromAttachment(beforePick.AttachmentID__c))
                        : null),
                    'beforeFileName' => (beforePick != null ? beforePick.Name : null),

                    'afterDocumentId' => null,
                    'afterContentDocumentId' => null,
                    'afterFileName' => null
                });
            } else {
                Documents__c beforePick = bestBefore.get(v.Id);
                if (beforePick == null) beforePick = bestLegacy.get(v.Id);

                Documents__c afterPick = bestAfter.get(v.Id);
                // Follow-up case logic:
                // - carried-forward rows are those NOT marked New_Violation__c
                // - new rows are those marked New_Violation__c = true
                Boolean isCarriedForward = (isFollowUp && v.New_Violation__c != true);

                rows.add(new Map<String, Object>{
                    'violationRecordId' => v.Id,
                    'sourceViolationRecordId' => null,

                    'name' => v.Heading__c,
                    'category' => v.Category__c,
                    'description' => v.Description__c,
                    'violationNumber' => v.Violation_Number__c,

                    'isFixed' => v.Is_Fixed__c,
                    'isFixedOnLoad' => v.Is_Fixed__c,

                    'isCarriedForward' => isCarriedForward,

                    'beforeDocumentId' => (beforePick != null ? beforePick.Id : null),
                    'beforeContentDocumentId' => (beforePick != null && resolveContentDocumentIdFromAttachment(beforePick.AttachmentID__c) != null
                        ? String.valueOf(resolveContentDocumentIdFromAttachment(beforePick.AttachmentID__c))
                        : null),
                    'beforeFileName' => (beforePick != null ? beforePick.Name : null),

                    'afterDocumentId' => (afterPick != null ? afterPick.Id : null),
                    'afterContentDocumentId' => (afterPick != null && resolveContentDocumentIdFromAttachment(afterPick.AttachmentID__c) != null
                        ? String.valueOf(resolveContentDocumentIdFromAttachment(afterPick.AttachmentID__c))
                        : null),
                    'afterFileName' => (afterPick != null ? afterPick.Name : null)
                });
            }
        }

        return JSON.serialize(new Map<String, Object>{
            'hasVsr' => true,
            // Always return current-case VSR id (uploads target this VSR)
            'vsrId' => currentVsr.Id,
            'vsrCaseId' => caseId,
            'vsrStatus' => currentVsr.Status__c,
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

        // Per requirement: file should only be linked to Documents__c (NOT to VSR__c)

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

        // Maintain Violation_Number__c sequencing after submit edits (category changes, deletions, etc.)
        recalcViolationNumbers(vsr.Id);

        // Per requirement: violations + their 3 placeholders are created before submit
        // (on Add / upload flows). Submit only updates VSR + existing violations.

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
                SELECT Id, Name, AttachmentID__c, Violation__c, VSR__c, Document_Type__c
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

            Id oldContentDocId = resolveContentDocumentIdFromAttachment(doc.AttachmentID__c);

            // Per requirement: document Name must come from Document_Configuration__mdt (not file name)
            String newName = configuredDocNameForType(doc.Document_Type__c, doc.Name);

            // Store "attachment record id" as the latest ContentVersion.Id
            Id newVersionId = getLatestContentVersionId(newCdId);
            String attachmentToStore = (newVersionId != null) ? String.valueOf(newVersionId) : contentDocumentId;

            update new Documents__c(
                Id = doc.Id,
                Name = newName,
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true,
                AttachmentID__c = attachmentToStore
            );

            safeEnsureLink(doc.Id, newCdId);
            removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, newCdId);

            // Ensure the uploaded file ends up linked ONLY to Documents__c (not to VSR__c or Violation__c).
            if (doc.VSR__c != null) deleteContentDocumentLinkIfExists(doc.VSR__c, newCdId);
            if (doc.Violation__c != null) {
                deleteContentDocumentLinkIfExists(doc.Violation__c, newCdId);
                try {
                    Id parentVsrId = [
                        SELECT VSR__c
                        FROM Violation__c
                        WHERE Id = :doc.Violation__c
                        LIMIT 1
                    ].VSR__c;
                    if (parentVsrId != null) deleteContentDocumentLinkIfExists(parentVsrId, newCdId);
                } catch (Exception ignore) {
                    // best-effort
                }
            }

            if (oldContentDocId != null && oldContentDocId != newCdId) {
                Set<Id> entities = new Set<Id>{ doc.Id };
                if (doc.VSR__c != null) entities.add(doc.VSR__c);
                if (doc.Violation__c != null) entities.add(doc.Violation__c);
                safeUnlinkAndDeleteIfOrphaned(oldContentDocId, entities);
            }

            return doc.Id;

        } catch (Exception e) {
            throw new AuraHandledException('updateViolationDocumentAfterUpload failed: ' + e.getMessage());
        }
    }

    @AuraEnabled
    public static Id createVillaPictureDocument(Id caseId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        // Legacy signature kept for compatibility; now villa doc is per VSR.
        List<VSR__c> vsrs = [
            SELECT Id
            FROM VSR__c
            WHERE Case__c = :caseId
            LIMIT 2
        ];
        if (vsrs.size() > 1) {
            throw new AuraHandledException('More than one VSR exists for this Case. Please contact support.');
        }
        if (vsrs.isEmpty()) {
            throw new AuraHandledException('No VSR exists for this Case.');
        }
        return ensureVillaDocumentForVsr(vsrs[0].Id);
    }

    // Create (if needed) and attach villa picture to current VSR
    @AuraEnabled
    public static Id upsertVillaAndAttach(Id caseId, Id vsrId, String fileName, String contentDocumentId) {
        if (caseId == null) throw new AuraHandledException('Case Id is required.');
        if (vsrId == null) throw new AuraHandledException('VSR Id is required.');
        if (String.isBlank(contentDocumentId)) throw new AuraHandledException('ContentDocumentId is required.');

        Id docId = ensureVillaDocumentForVsr(vsrId);
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

        // Legacy method kept for compatibility; now create violation + 3 placeholders first.
        Map<String, Object> created = createViolationWithPlaceholders(caseId, vsrId, heading, category, description, false);
        Id vioId = (Id) created.get('violationId');
        Id beforeDocId = (Id) created.get('beforeDocumentId');

        // Attach uploaded file to the requested placeholder (defaults to BEFORE)
        Id targetDocId = beforeDocId;
        if (!String.isBlank(documentType)) {
            for (Documents__c d : [
                SELECT Id
                FROM Documents__c
                WHERE Violation__c = :vioId
                AND Document_Type__c = :documentType
                LIMIT 1
            ]) {
                targetDocId = d.Id;
            }
        }
        updateViolationDocumentAfterUpload(targetDocId, fileName, contentDocumentId);
        return new Map<String, Object>{ 'violationId' => vioId, 'documentId' => targetDocId };
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

        // Create the 3 placeholders for the new child violation
        Map<String, Id> docIds = ensureViolationPlaceholdersInternal(child.Id);
        Id beforeDocId = docIds.get('BEFORE');
        Id afterDocId  = docIds.get('AFTER');

        // BEFORE: clone parent's ContentDocument so child attachment is independent
        if (!String.isBlank(beforeContentDocumentId) && beforeDocId != null) {
            Id parentCdId = null;
            try { parentCdId = (Id) beforeContentDocumentId; } catch (Exception ignore) { parentCdId = null; }

            if (parentCdId != null) {
                try {
                    Id newChildVersionId = cloneContentDocumentToFirstPublishLocationReturnVersionId(parentCdId, beforeDocId);
                    if (newChildVersionId != null) {
                        update new Documents__c(
                            Id = beforeDocId,
                            AttachmentID__c = String.valueOf(newChildVersionId),
                            Status__c = 'Submitted',
                            Is_Customer_Doc__c = true
                        );
                    }
                } catch (Exception e) {
                    // If cloning fails, fall back to linking (but deletion logic prevents parent deletion).
                    Id parentVerId = getLatestContentVersionId(parentCdId);
                    update new Documents__c(
                        Id = beforeDocId,
                        AttachmentID__c = String.valueOf(parentVerId),
                        Status__c = 'Submitted',
                        Is_Customer_Doc__c = true
                    );
                    // best-effort: do not force-link to keep "doc-only" rule; parent file remains on parent doc.
                }
            }
        }

        // AFTER: attach uploaded file
        if (afterDocId != null) {
            updateViolationDocumentAfterUpload(afterDocId, afterFileName, afterContentDocumentId);
        }

        return new Map<String, Object>{
            'violationId' => child.Id,
            'afterDocumentId' => afterDocId,
            'beforeDocumentId' => beforeDocId
        };
    }

    // Immediate delete when user deletes a row (draft-safe)
    @AuraEnabled
    public static void deleteViolationAndFiles(Id violationId) {
        if (violationId == null) throw new AuraHandledException('Violation Id is required.');

        Violation__c v = [
            SELECT Id, Is_Fixed__c, VSR__c
            FROM Violation__c
            WHERE Id = :violationId
            LIMIT 1
        ];
        if (v.Is_Fixed__c == true) throw new AuraHandledException('Fixed violations cannot be deleted.');

        deleteDocumentsAndFilesByViolationIds(new Set<Id>{ violationId });
        delete v;
        // Re-sequence numbers after deletion
        if (v.VSR__c != null) recalcViolationNumbers(v.VSR__c);
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

            Id oldContentDocId = resolveContentDocumentIdFromAttachment(doc.AttachmentID__c);

            Map<String, String> types = getRequiredVsrTypes();
            String villaType = types.get('VILLA');
            String villaName = requireVsrCfg(CFG_NAME_VILLA).Document_Name__c;

            Id newVersionId = getLatestContentVersionId(newCdId);
            String attachmentToStore = (newVersionId != null) ? String.valueOf(newVersionId) : contentDocumentId;

            update new Documents__c(
                Id = doc.Id,
                Name = villaName,
                Document_Type__c = villaType,
                Status__c = 'Submitted',
                Is_Customer_Doc__c = true,
                Mandatory__c = true,
                AttachmentID__c = attachmentToStore
            );

            ensureLink(doc.Id, newCdId);

            // Per requirement: file must NOT remain linked to VSR__c.
            if (doc.VSR__c != null) deleteContentDocumentLinkIfExists(doc.VSR__c, newCdId);
            removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, newCdId);

            if (oldContentDocId != null && oldContentDocId != newCdId) {
                Set<Id> entities = new Set<Id>{ doc.Id };
                if (doc.VSR__c != null) entities.add(doc.VSR__c);
                safeUnlinkAndDeleteIfOrphaned(oldContentDocId, entities);
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
        // Deprecated: per requirement, villa file should only be linked to Documents__c.
        // Intentionally left empty for backward compatibility.
    }

    private static void deleteDocumentsAndFilesByViolationIds(Set<Id> violationIds) {
        if (violationIds == null || violationIds.isEmpty()) return;

        List<Documents__c> docs = [
            SELECT Id, AttachmentID__c, VSR__c, Violation__c
            FROM Documents__c
            WHERE Violation__c IN :violationIds
        ];

        for (Documents__c d : docs) {
            if (String.isBlank(d.AttachmentID__c)) continue;
            Id cdId = resolveContentDocumentIdFromAttachment(d.AttachmentID__c);
            if (cdId == null) continue;

            Set<Id> entities = new Set<Id>{ d.Id };
            if (d.VSR__c != null) entities.add(d.VSR__c);
            if (d.Violation__c != null) entities.add(d.Violation__c);
            safeUnlinkAndDeleteIfOrphaned(cdId, entities);
        }

        if (!docs.isEmpty()) delete docs;
    }

    private static void deleteDocumentsAndFilesByDocumentIds(Set<Id> documentIds) {
        if (documentIds == null || documentIds.isEmpty()) return;

        List<Documents__c> docs = [
            SELECT Id, AttachmentID__c, VSR__c, Violation__c
            FROM Documents__c
            WHERE Id IN :documentIds
        ];

        for (Documents__c d : docs) {
            if (String.isBlank(d.AttachmentID__c)) continue;
            Id cdId = resolveContentDocumentIdFromAttachment(d.AttachmentID__c);
            if (cdId == null) continue;

            Set<Id> entities = new Set<Id>{ d.Id };
            if (d.VSR__c != null) entities.add(d.VSR__c);
            if (d.Violation__c != null) entities.add(d.Violation__c);
            safeUnlinkAndDeleteIfOrphaned(cdId, entities);
        }

        if (!docs.isEmpty()) delete docs;
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

    private static void safeUnlinkAndDeleteIfOrphaned(Id contentDocumentId, Set<Id> linkedEntityIdsToUnlink) {
        if (contentDocumentId == null) return;
        Set<Id> ids = (linkedEntityIdsToUnlink == null) ? new Set<Id>() : new Set<Id>(linkedEntityIdsToUnlink);
        ids.remove(null);

        // Remove links only from the provided entities (do NOT delete all links globally).
        if (!ids.isEmpty()) {
            try {
                List<ContentDocumentLink> links = [
                    SELECT Id
                    FROM ContentDocumentLink
                    WHERE ContentDocumentId = :contentDocumentId
                    AND LinkedEntityId IN :ids
                ];
                if (!links.isEmpty()) Database.delete(links, false);
            } catch (Exception e) {
                System.debug('safeUnlinkAndDeleteIfOrphaned: link delete failed: ' + e.getMessage());
            }
        }

        // Delete the ContentDocument only if it has no remaining links.
        try {
            Integer remaining = [
                SELECT COUNT()
                FROM ContentDocumentLink
                WHERE ContentDocumentId = :contentDocumentId
            ];
            if (remaining == 0) {
                Database.delete(new List<ContentDocument>{ new ContentDocument(Id = contentDocumentId) }, false);
            }
        } catch (Exception e) {
            System.debug('safeUnlinkAndDeleteIfOrphaned: content doc delete failed: ' + e.getMessage());
        }
    }

    /**
     * Requirement: Documents__c.AttachmentID__c must store the "attachment record id" (ContentVersion.Id).
     * - When we need a ContentDocumentId for links/thumbnails/deletes, we resolve it from that value.
     * - Backward compatible: if AttachmentID__c already stores a ContentDocumentId, this returns it.
     */
    private static Id resolveContentDocumentIdFromAttachment(String attachmentIdVal) {
        if (String.isBlank(attachmentIdVal)) return null;
        Id raw;
        try { raw = (Id) attachmentIdVal; } catch (Exception e) { return null; }

        // Try as ContentVersion.Id
        try {
            List<ContentVersion> cvs = [
                SELECT ContentDocumentId
                FROM ContentVersion
                WHERE Id = :raw
                LIMIT 1
            ];
            if (!cvs.isEmpty() && cvs[0].ContentDocumentId != null) return cvs[0].ContentDocumentId;
        } catch (Exception ignore) {}

        // Otherwise assume it's already a ContentDocumentId
        return raw;
    }

    private static Id getLatestContentVersionId(Id contentDocumentId) {
        if (contentDocumentId == null) return null;
        try {
            return [
                SELECT Id
                FROM ContentVersion
                WHERE ContentDocumentId = :contentDocumentId
                AND IsLatest = true
                LIMIT 1
            ].Id;
        } catch (Exception e) {
            return null;
        }
    }

    private static Id cloneContentDocumentToFirstPublishLocationReturnVersionId(Id sourceContentDocumentId, Id firstPublishLocationId) {
        if (sourceContentDocumentId == null || firstPublishLocationId == null) return null;

        ContentVersion src = [
            SELECT Id, Title, FileExtension, VersionData, PathOnClient
            FROM ContentVersion
            WHERE ContentDocumentId = :sourceContentDocumentId
            AND IsLatest = true
            LIMIT 1
        ];

        String path = src.PathOnClient;
        if (String.isBlank(path)) {
            String ext = String.isBlank(src.FileExtension) ? '' : ('.' + src.FileExtension);
            path = (String.isBlank(src.Title) ? 'File' : src.Title) + ext;
        }

        ContentVersion cv = new ContentVersion();
        cv.FirstPublishLocationId = firstPublishLocationId;
        cv.Title = src.Title;
        cv.PathOnClient = path;
        cv.VersionData = src.VersionData;
        insert cv;

        return cv.Id;
    }

    private static Id cloneContentDocumentToFirstPublishLocation(Id sourceContentDocumentId, Id firstPublishLocationId) {
        if (sourceContentDocumentId == null || firstPublishLocationId == null) return null;

        ContentVersion src = [
            SELECT Id, Title, FileExtension, VersionData, PathOnClient
            FROM ContentVersion
            WHERE ContentDocumentId = :sourceContentDocumentId
            AND IsLatest = true
            LIMIT 1
        ];

        String path = src.PathOnClient;
        if (String.isBlank(path)) {
            String ext = String.isBlank(src.FileExtension) ? '' : ('.' + src.FileExtension);
            path = (String.isBlank(src.Title) ? 'File' : src.Title) + ext;
        }

        ContentVersion cv = new ContentVersion();
        cv.FirstPublishLocationId = firstPublishLocationId;
        cv.Title = src.Title;
        cv.PathOnClient = path;
        cv.VersionData = src.VersionData;
        insert cv;
        return cv.ContentDocumentId;
    }

    private static void recalcViolationNumbers(Id vsrId) {
        if (vsrId == null) return;

        List<Violation__c> vios = [
            SELECT Id, Category__c, CreatedDate
            FROM Violation__c
            WHERE VSR__c = :vsrId
            ORDER BY CreatedDate ASC
        ];
        if (vios.isEmpty()) return;

        Map<String, List<Violation__c>> byLetter = new Map<String, List<Violation__c>>{
            'A' => new List<Violation__c>(),
            'B' => new List<Violation__c>(),
            'C' => new List<Violation__c>()
        };
        List<Violation__c> noCat = new List<Violation__c>();

        for (Violation__c v : vios) {
            String letter = letterForCategory(v.Category__c);
            if (letter == 'A' || letter == 'B' || letter == 'C') byLetter.get(letter).add(v);
            else noCat.add(v);
        }

        List<Violation__c> toUpdate = new List<Violation__c>();
        for (String letter : new List<String>{ 'A', 'B', 'C' }) {
            List<Violation__c> group = byLetter.get(letter);
            Integer i = 0;
            for (Violation__c v : group) {
                i++;
                toUpdate.add(new Violation__c(
                    Id = v.Id,
                    Violation_Number__c = 'ITEM ' + letter + '.' + String.valueOf(i)
                ));
            }
        }
        for (Violation__c v : noCat) {
            toUpdate.add(new Violation__c(Id = v.Id, Violation_Number__c = null));
        }

        if (!toUpdate.isEmpty()) {
            if (!Schema.sObjectType.Violation__c.isUpdateable()) {
                throw new AuraHandledException('You do not have permission to update Violation records.');
            }
            update toUpdate;
        }
    }

    private static String letterForCategory(String category) {
        String c = String.isBlank(category) ? '' : category.trim().toLowerCase();
        if (c == 'yellow') return 'A';
        if (c == 'orange') return 'B';
        if (c == 'red') return 'C';
        return null;
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

    // buildDocumentDisplayName/stripExtension intentionally removed from active use:
    // Document naming is now driven by Document_Configuration__mdt (per requirement).

    // Remove a specific file link from a specific entity (best-effort).
    // Used to ensure violation uploads do not remain linked to the VSR record.
    private static void deleteContentDocumentLinkIfExists(Id linkedEntityId, Id contentDocumentId) {
        if (linkedEntityId == null || contentDocumentId == null) return;
        try {
            List<ContentDocumentLink> links = [
                SELECT Id
                FROM ContentDocumentLink
                WHERE LinkedEntityId = :linkedEntityId
                AND ContentDocumentId = :contentDocumentId
            ];
            if (!links.isEmpty()) {
                Database.delete(links, false);
            }
        } catch (Exception ignore) {
            // ignore
        }
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
            // Create villa placeholder if needed (submit requires it for Clear/Clear with Comments)
            doc = (vsrId != null) ? getVillaDocForVsr(vsrId, getRequiredVsrTypes().get('VILLA')) : null;
        }

        if (doc == null) return null;

        if (vsrId != null && doc.VSR__c != vsrId) {
            doc.VSR__c = vsrId;
            doc.Document_Type__c = getRequiredVsrTypes().get('VILLA');
            doc.Name = requireVsrCfg(CFG_NAME_VILLA).Document_Name__c;
            update doc;
        }

        // NO carry-forward of villa picture. Only enforce link to Documents__c (NOT to VSR__c).
        if (!String.isBlank(doc.AttachmentID__c)) {
            Id cdId = resolveContentDocumentIdFromAttachment(doc.AttachmentID__c);

            if (cdId != null) {
                ensureLink(doc.Id, cdId);
                if (vsrId != null) deleteContentDocumentLinkIfExists(vsrId, cdId);
                removeOtherFilesFromEntities(new Set<Id>{ doc.Id }, cdId);
            }
        }

        return doc;
    }

    private static Documents__c getVillaDocForVsr(Id vsrId, String villaType) {
        if (vsrId == null || String.isBlank(villaType)) return null;
        List<Documents__c> docs = [
            SELECT Id, Name, AttachmentID__c, VSR__c, Document_Type__c, CreatedDate
            FROM Documents__c
            WHERE VSR__c = :vsrId
            AND Document_Type__c = :villaType
            ORDER BY CreatedDate DESC
            LIMIT 1
        ];
        return docs.isEmpty() ? null : docs[0];
    }

    private static Id getParentVillaContentDocumentId(Id parentCaseId, Id parentVsrId) {
        if (parentCaseId == null) return null;
        // Deprecated / no longer used. Intentionally returns null.
        // (Previously contained an unsafe "latest doc" query without a where-clause.)
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
            SELECT Id, AttachmentID__c, VSR__c, Violation__c
            FROM Documents__c
            WHERE Id = :documentId
            LIMIT 1
        ];

        Id oldContentDocId = resolveContentDocumentIdFromAttachment(doc.AttachmentID__c);

        if (!String.isBlank(doc.AttachmentID__c)) {
            doc.AttachmentID__c = null;
            update doc;
        }

        if (oldContentDocId != null) {
            Set<Id> entities = new Set<Id>{ doc.Id };
            if (doc.VSR__c != null) entities.add(doc.VSR__c);
            if (doc.Violation__c != null) entities.add(doc.Violation__c);
            safeUnlinkAndDeleteIfOrphaned(oldContentDocId, entities);
        }
    }
}

