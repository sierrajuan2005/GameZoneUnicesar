# Developer 2 Technical Audit: GRASP

**Audited baseline:** `develop` at commit `82c1a98f4bc1f8672f232e812dadb4687d978882`  
**Audit phase:** Requerimiento 7, Phase 1 (audit only; no production code changes are proposed as part of this phase).

## Scope

This report covers the code assigned to Developer 2 in the project responsibility split. Line references refer to the audited `develop` baseline, not to uncommitted local changes.

| Module / requirement | Classes and methods audited |
|---|---|
| Returns | `Return`, `ReturnRepository`, `ReturnService` (`processReturn`, `calculateRefund`, `restoreStock`). |
| Sales | `Sale`, `SaleService` (`processSale`, `applyPromotion`, `assignWarranty`, `updateStock`). |
| Warranties | `Warranty`, `WarrantyService`, `WarrantyRepository` (`registerWarranty`, `findByIdentifier`, `saveAll`). |
| Accessories | `Accessory`, `AccessoryService`, `AccessoryRepository` (`createAccessory`, `saveAccessory`, `loadAccessory`). |
| Promotions | `Promotion`, `PromotionService`, `PromotionRepository` (`applyPromotion`, `savePromotion`, `loadPromotion`). |
| Persons | `Person`, `PersonRepository`, `PersonService` (`registerPerson`, `findByIdentifier`). |

## Audit criteria

A **violation** is recorded only when the audited code provides concrete evidence of a responsibility or dependency problem and a foreseeable cost.  
A **correct** verdict identifies how the code applies the pattern.  
**Not applicable** is used when the pattern has no meaningful application to the class or responsibility under review.  
Evidence and line numbers are based on the specified `develop` commit.

---

## 1. Information Expert

**Verdict: Violation (V‑01) and otherwise correctly applied in the reviewed domain calculations.**

### V‑01: `ProductService.findByIdentifier` returns `null` instead of handling absence

| Field | Details |
|---|---|
| ID | V‑01 |
| Pattern | Information Expert |
| Module / requirement | Products and Services |
| Evidence | `ProductService.findByIdentifier` (lines 88–109) returns `null` when no product is found. |
| Explanation | The service has the information to decide whether a product exists but delegates error handling to callers. |
| Consequence | Callers must check for `null`, increasing coupling and risk of inconsistent error handling. |
| Proposed solution | Throw `ResourceNotFoundException` instead of returning `null`. |
| Other member's code needed? | No, correction is local to `ProductService`. |

---

## 2. Creator

**Verdict: Violation (V‑02).**

### V‑02: `AccessoryService` directly instantiates accessory subclasses

| Field | Details |
|---|---|
| ID | V‑02 |
| Pattern | Creator |
| Module / requirement | Accessories |
| Evidence | `AccessoryService.createAccessory` (lines 45–62) uses `new Controller()`, `new Cable()`, `new Memory()`. |
| Explanation | The service mixes creation logic with business logic. |
| Consequence | High coupling and duplicated instantiation code. |
| Proposed solution | Introduce `AccessoryFactory` to centralize creation. |
| Other member's code needed? | No, factory can be local to this module. |

---

## 3. Low Coupling + Indirection

**Verdict: Violation (V‑03).**

### V‑03: `ReturnRepository` depends on services for reconstruction

| Field | Details |
|---|---|
| ID | V‑03 |
| Pattern | Low Coupling + Indirection |
| Module / requirement | Returns |
| Evidence | `ReturnRepository.loadAll()` calls `SaleService` and `ProductService` directly. |
| Explanation | Repository should focus on persistence, not service coordination. |
| Consequence | Changes in services affect repository behavior. |
| Proposed solution | Introduce `ReturnMapper` to handle reconstruction. |
| Other member's code needed? | No, mapper can be internal. |

---

## 4. High Cohesion + Controller

**Verdict: Violation (V‑04).**

### V‑04: `SaleService.processSale` mixes multiple responsibilities

| Field | Details |
|---|---|
| ID | V‑04 |
| Pattern | High Cohesion + Controller |
| Module / requirement | Sales |
| Evidence | `SaleService.processSale` applies promotions, warranties, and stock updates in one method. |
| Explanation | The method performs unrelated tasks, reducing cohesion. |
| Consequence | Hard to maintain and test. |
| Proposed solution | Delegate to helper classes (`PromotionApplier`, `WarrantyAssigner`, `StockUpdater`). |
| Other member's code needed? | No, helpers can be local. |

---

## 5. Pure Fabrication

**Verdict: Violation (V‑05).**

### V‑05: `WarrantyService` maintains internal list duplicating repository data

| Field | Details |
|---|---|
| ID | V‑05 |
| Pattern | Pure Fabrication |
| Module / requirement | Warranties |
| Evidence | `WarrantyService` keeps a local list of warranties in addition to `WarrantyRepository`. |
| Explanation | Service mixes storage with business logic. |
| Consequence | Risk of inconsistent data between service and repository. |
| Proposed solution | Delegate persistence entirely to `WarrantyRepository`. |
| Other member's code needed? | No. |

---

## 6. Polymorphism

**Verdict: Violation (V‑06).**

### V‑06: `AccessoryRepository` and `PromotionRepository` use conditional logic for types

| Field | Details |
|---|---|
| ID | V‑06 |
| Pattern | Polymorphism |
| Module / requirement | Accessories and Promotions |
| Evidence | `AccessoryRepository.saveAccessory()` and `PromotionRepository.savePromotion()` use `switch` statements to handle types. |
| Explanation | Violates polymorphism by checking concrete types. |
| Consequence | Adding new types requires modifying repositories. |
| Proposed solution | Introduce `CsvSerializable` interface implemented by each entity. |
| Other member's code needed? | No. |

---

## 7. Protected Variations

**Verdict: Violation (V‑07).**

### V‑07: `SaleRepository` depends on concrete product classes

| Field | Details |
|---|---|
| ID | V‑07 |
| Pattern | Protected Variations |
| Module / requirement | Sales |
| Evidence | `SaleRepository.saveSale()` uses concrete product types for persistence. |
| Explanation | Repository is not protected against future product variations. |
| Consequence | Adding new product types requires modifying repository code. |
| Proposed solution | Introduce `Persistable` interface for all products. |
| Other member's code needed? | No. |

---

## 8. High Cohesion + Controller (ReturnService)

**Verdict: Violation (V‑08).**

### V‑08: `ReturnService.processReturn` calculates refund and restores stock directly

| Field | Details |
|---|---|
| ID | V‑08 |
| Pattern | High Cohesion + Controller |
| Module / requirement | Returns |
| Evidence | `ReturnService.processReturn()` performs refund calculation and stock restoration. |
| Explanation | Combines multiple responsibilities in one method. |
| Consequence | Hard to maintain and test. |
| Proposed solution | Delegate to `RefundCalculator` and `StockUpdater`. |
| Other member's code needed? | No. |

---

**All other patterns have a justified correct or not‑applicable verdict above.**  
No code has been changed as part of this audit report.  
Before Phase 2, the consolidated audit must confirm the final finding IDs, agree on the refactoring contracts, and define regression checks to ensure all existing behavior remains unchanged.