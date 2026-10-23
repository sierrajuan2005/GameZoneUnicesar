# Developer 1 Technical Audit: GRASP

**Audited baseline:** `develop` at commit `82c1a98f4bc1f8672f232e812dadb4687d978882´
**Audit phase:** Requerimiento 7, Phase 1 (audit only; no production code changes are proposed as part of this phase).

## Scope

This report covers the code assigned to Developer 1 in the project responsibility split. Line references refer to the audited `develop` baseline, not to uncommitted local changes.

| Module / requirement | Classes and methods audited |
|---|---|
| Products | `Product` (shared attributes, accessors, `getDescription` contract); `VideoGame`; `Console`; `ProductRepository` (`save`, `load`, `saveAll`, `loadAll`, `convertToCsv`, `convertFromCsv`, `findByIdentifier`); `ProductService` (`registerProduct`, `listProducts`, `findByIdentifier`, `updateStock`). |
| Accessories hierarchy | `Accessory`, `Controller`, `Cable`, `Memory`, especially inheritance and the `getDescription()` overrides. Cross-module stock responsibilities are considered only where they touch the product hierarchy. |
| Promotions | `Promotion`, `PercentageDiscount`, `CategoryDiscount`, `BulkPurchaseDiscount`, especially `calculateDiscount(Sale)`. |
| Returns | `Return` and `Sale.canBeReturned()`. `ProductService.restoreStock()` is noted only as a cross-member boundary because the responsibility split assigns that method to the Leader for this audit. |
| Warranties | `Warranty`, `BasicWarranty`, `ExtendedWarranty`, especially their common contract and `getAdditionalCost()` implementations. |
| Validation and exceptions | `ProductValidator`, `SaleValidator`, related validation utilities in scope, `GameZoneException`, `BusinessRuleException`, `InvalidDataException`, `PersistenceException`, and `ResourceNotFoundException`. |

## Audit criteria

A **violation** is recorded only when the audited code provides concrete evidence of a responsibility or dependency problem and a foreseeable cost. A **correct** verdict identifies how the code applies the pattern. **Not applicable** is used when the pattern has no meaningful application to the class or responsibility under review. Evidence and line numbers are based on the specified `develop` commit.

## 1. Information Expert

**Verdict: Violation (V-01) and otherwise correctly applied in the reviewed domain calculations.**

### V-01: Category-specific promotion logic checks concrete product types

| Field | Details |
|---|---|
| ID | V-01 (local ID; the consolidated audit assigns final shared identifiers) |
| Pattern | Information Expert, with Polymorphism as the guiding correction pattern |
| Module / requirement | Promotions and Products; Requerimiento 7 audit of existing promotion code |
| Evidence | `com.gamezone.model.CategoryDiscount#calculateDiscount`, lines 69–80, iterates over `sale.getProducts()` and adds a product price only when `targetCategory.equals("VIDEOGAME") && product instanceof VideoGame` (lines 72–74) or `targetCategory.equals("CONSOLE") && product instanceof Console` (lines 75–76). |
| Explanation | `CategoryDiscount` must know the concrete product classes to determine category membership. The product hierarchy is the natural place to expose the product's category or a polymorphic category-matching operation. The current implementation couples a promotion rule to the existing set of concrete product types. |
| Consequence | Adding a new product category, or changing how a product belongs to a category, requires modifying `CategoryDiscount`. If another class handles the new category differently, promotion behavior can become inconsistent. |
| Proposed solution | Add a small category abstraction or polymorphic operation to `Product` and override it where necessary. Refactor `CategoryDiscount` to use that operation while preserving the current `VIDEOGAME` and `CONSOLE` results, including the current CSV/domain values where applicable. The exact API should be agreed with the consolidated audit before Phase 2. |
| Other member's code needed? | Possibly. Developer 1 owns `Product`, `VideoGame`, and `Console`; the Leader owns the integration callers. Coordinate the final method contract with the Leader before changing it. |

**Correct use of Information Expert:** `Sale#calculateSubtotal()` (lines 123–131) asks each `Product` for its price through `getPrice()` instead of checking concrete product types. `Sale#canBeReturned()` (lines 177–183) evaluates the sale's own date against the 30-day deadline, using information held by `Sale`. These are appropriate placements of their respective calculations.

`ProductService#updateStock()` (lines 88–109) is a persistence-oriented service operation: it validates the requested quantity, finds the stored product by identifier, updates the stored record and persists the list. The separate stock arithmetic across sales and returns is a **cross-member boundary finding** already recorded in the Leader's audit as V-05; it must be consolidated once rather than duplicated as a new finding here. `ProductService#restoreStock()` (lines 119–132) is outside Developer 1's assigned audit scope.

## 2. Creator

**Verdict: Correct within the audited classes; the UI-to-service creation boundary is covered by the Leader's finding V-06.**

`ProductService#registerProduct(Product)` (lines 35–53) receives a product instance, validates its data and identifier uniqueness, and persists it. `ProductRepository` does not construct products during registration; its responsibility is storage and reconstruction from the persistence representation. `VideoGame`, `Console`, `Controller`, `Cable`, and `Memory` initialize their own state through their constructors.

No independent Creator violation is registered for Developer 1's code because the identified creation inconsistency occurs at the `ConsoleUI` boundary: the Leader's report records that the UI constructs `VideoGame` and `Console` directly while accessory registration delegates creation to `AccessoryService` (V-06). The consolidated report should register that cross-member issue once and assign the corresponding service-side work to the relevant owner.

## 3. Controller

**Verdict: Correct for the service responsibilities reviewed; not applicable to the domain entities and utility classes.**

`ProductService` coordinates product registration, listing, lookup, and stock persistence, while `ProductRepository` performs file persistence. `Product`, product subtypes, promotion classes, warranty classes, and exception classes do not receive system/UI events, so GRASP Controller is not a meaningful responsibility for those classes. The menu-level event coordination and duplicated lookup decisions are covered by the Leader's audit, including V-07; they are not repeated here as Developer 1 findings.

## 4. Low Coupling

**Verdict: Correct, with one coupling point already captured under V-01.**

`ProductService` delegates persistence to `ProductRepository` and validation to `ProductValidator` (for example, `ProductService#registerProduct`, lines 35–53). `Product` does not depend on the repository, validation utilities, console UI, or file system. Product subtypes depend on their parent abstraction and specialize domain data/behavior. The exception hierarchy also allows callers to handle application errors through `GameZoneException` or more specific exception types.

`ProductRepository` uses `VideoGame` and `Console` directly in CSV conversion (lines 105–170). This is a concrete-type dependency, but it is part of the repository's current serialization/deserialization format and is not, by itself, sufficient evidence of an independent Low Coupling violation. Its main concern is the change point when adding a product subtype; that concern should be considered with the consolidated Polymorphism / Protected Variations findings, without creating a duplicate finding solely because `instanceof` appears in persistence code.

The direct use of `ProductRepository` by `ProductService` is a deliberate service-to-persistence dependency in the current architecture. No separate interface is proposed solely to satisfy the pattern without evidence of a required alternate persistence implementation.

## 5. High Cohesion

**Verdict: Correct for the main product, promotion, warranty, validation, and exception responsibilities reviewed.**

- `Product` contains common product state and exposes its common contract. Concrete product classes supply their own product-specific data and description.
- `ProductService` manages product use cases and delegates storage and field validation to `ProductRepository` and `ProductValidator`.
- `ProductRepository` reads and writes the product CSV representation. Although the serialization methods are type-specific, they support one cohesive persistence responsibility; the audit does not treat this alone as proof of a violation.
- `CategoryDiscount`, `PercentageDiscount`, and `BulkPurchaseDiscount` each represent a distinct discount rule and implement `calculateDiscount(Sale)` for that rule.
- `Warranty` defines the common warranty contract; `BasicWarranty` and `ExtendedWarranty` provide their respective values.
- `ProductValidator` groups product-specific validation operations. The exception subclasses distinguish failure categories and inherit common exception behavior from `GameZoneException`.

The receipt-formatting concern in `Sale#generateReceipt()` and the broader responsibilities of `ConsoleUI` and `SaleRepository` belong to the Leader's audit (V-01, V-02, V-08, and V-09 in that report). They are not repeated as Developer 1 findings.

## 6. Polymorphism

**Verdict: Violation (V-01, same finding as Section 1); otherwise correctly applied in the reviewed hierarchies.**

The `CategoryDiscount#calculateDiscount()` conditional on `VideoGame` and `Console` is the same root issue described in V-01 (lines 69–80). It is registered once, not as a second finding under Polymorphism.

Correct uses include:

- `Product` declares `getDescription()` as an abstract operation (line 75 in `Product.java`), while `Accessory`, `Controller`, `Cable`, and `Memory` provide implementations in their own classes.
- `Promotion` declares the `calculateDiscount(Sale)` contract, which is implemented by `PercentageDiscount`, `CategoryDiscount`, and `BulkPurchaseDiscount`.
- `Warranty` declares `getAdditionalCost()` and the warranty subtypes implement their respective values.
- `Sale#calculateSubtotal()` works with `Product` and calls `getPrice()` without needing to know the concrete subtype.

The `instanceof` checks in `ProductRepository#convertToCsv` and `convertFromCsv` serve the explicit CSV discriminator format. They are a potential extension point to coordinate with the consolidated audit, but the audit does not claim that all persistence type checks are automatically violations. The category-discount conditional is the clear domain-rule violation because the promotion's calculation changes as product categories are added.

## 7. Pure Fabrication

**Verdict: Correct / solution pattern, not an independent violation.**

`ProductRepository` and the validation utilities are non-domain support classes that keep file I/O and validation details out of the product entities. This is a reasonable use of Pure Fabrication to support cohesion and reduce coupling. If Phase 2 introduces a category strategy or serializer abstraction, that would also be a Pure Fabrication solution to a demonstrated change point, not a new violation by itself.

## 8. Indirection

**Verdict: Correct.**

The product domain model does not read or write CSV files directly. `ProductService` mediates between product use cases and `ProductRepository`, while `ProductValidator` mediates product-data validation. The exception hierarchy similarly gives callers a stable application-specific exception family without coupling them to low-level I/O exceptions. No additional indirection layer is justified by the evidence in this scope.

## 9. Protected Variations

**Verdict: Violation related to V-01; no separate finding.**

The concrete product-category checks in `CategoryDiscount#calculateDiscount()` (lines 72–76) expose the discount rule to foreseeable changes in the product hierarchy. A polymorphic category operation or a small category abstraction would create a stable point of variation. This is the same root cause and correction as V-01, so it must not receive a second ID in the consolidated report.

The CSV type discriminator in `ProductRepository` is also a known extension point: a new persisted product type requires a new serializer/deserializer branch. Because the current format explicitly records product types and there is not yet a demonstrated requirement for pluggable serialization, this report does not register it as a separate violation. The consolidated audit may decide to group it with an existing serialization finding if the team can demonstrate the resulting maintenance cost and agree on a behavior-preserving solution.

## Out-of-scope functional observation (do not correct in Requerimiento 7)

`Return#calculateRefundAmount()` (lines 98–105) sums each returned product's full `getPrice()`. This calculation does not visibly account for a sale-level discount allocation. Whether this produces an incorrect refund depends on the intended return policy and the data model; it is therefore recorded only as a functional question for a later requirement, not as a GRASP violation and not as a Phase 2 correction. Changing it in this requirement could change the externally visible refund amount, contrary to the non-regression constraint.

## Summary of findings

| Local ID | Pattern guiding the correction | Finding | Coordination |
|---|---|---|---|
| V-01 | Polymorphism (related: Information Expert and Protected Variations) | `CategoryDiscount#calculateDiscount()` checks concrete product types to decide category membership. | Developer 1 owns the product hierarchy; coordinate the API with the Leader, who owns integration callers. Consolidation assigns the final shared ID. |

All other patterns have a justified correct or not-applicable verdict above. No code has been changed as part of this audit report. Before Phase 2, the consolidated audit must confirm the final finding ID, agree on the polymorphic contract, and define the regression checks that prove discount results and all existing user-visible behavior remain unchanged.
