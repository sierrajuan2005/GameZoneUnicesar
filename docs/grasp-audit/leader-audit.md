# Leader Technical Audit: GRASP

Audited commit of `develop`: <paste hash from git log -1 --format=%H>

## Scope

In the repository the console menu class is named `ConsoleUI` (called `ConsoleMenu` in the assignment).

| Module (requirement) | Class / method audited |
|---|---|
| Sales (Taller 1) | Sale, SaleRepository, SaleService, ConsoleUI, Main |
| Accessories (Req. 1) | Integration in SaleService and ConsoleUI |
| Promotions (Req. 2) | Discount application in SaleService.registerSale; changes in Sale (applied discount, generateReceipt); integration in ConsoleUI |
| Returns (Req. 3) | ProductService.restoreStock; integration in ConsoleUI |
| Warranties (Req. 4) | Warranty assignment and extended warranty cost in SaleService.registerSale; integration in ConsoleUI |
| Exceptions (Req. 6) | Differentiated exception handling in ConsoleUI |

Not audited by the Leader: `Sale.canBeReturned()` (Developer 1, Req. 3).

## 1. Information Expert
Verdict: violation (pending Main and restoreStock)

### V-05

| Field | Content |
|---|---|
| ID | V-05 |
| Pattern | Information Expert |
| Module | Sales (Taller 1), Accessories (Req. 1) |
| Evidence | `SaleService#registerSale` computes `storedAccessory.getAvailableQuantity() - 1` and `storedProduct.getAvailableQuantity() - 1`, then calls `accessoryService.updateStock(id, newQuantity)` / `productService.updateStock(product, newQuantity)`. Private helpers `findProductById` and `findAccessoryById` search the lists returned by `productService.listProducts()` and `accessoryService.listAllAccessories()`. Lines: <fill in>. |
| Explanation | The stock data belongs to `Product`/`Accessory` and the lookup to `ProductService`/`AccessoryService`. `SaleService` reads their data, computes the new value and writes it back, instead of asking the owner to perform the operation. The two services also expose different update signatures. |
| Consequence | Any change in stock rules must be replicated in `SaleService` and kept consistent with `ProductService.restoreStock` (Req. 3). |
| Proposed solution | Ask the owners: a "reduce stock" operation and a lookup in `ProductService`/`AccessoryService` (counterpart of `restoreStock`), used by `SaleService`. Same stock results. |
| Other member's code | Yes: Developer 1 (`Product`, `ProductService`) and Developer 2 (`Accessory`, `AccessoryService`). To settle in consolidation. |

## 2. Creator
Verdict: violation (V-06). `SaleService` and `SaleRepository` are correct; Main pending.

### V-06

| Field | Content |
|---|---|
| ID | V-06 |
| Pattern | Creator |
| Module | Sales (Taller 1), Products and Persons (Taller 1, integration in the UI) |
| Evidence | `ConsoleUI#registerVideoGame` (`new VideoGame(...)`), `ConsoleUI#registerConsole` (`new Console(...)`), `ConsoleUI#registerCustomer` (`new Customer(...)`) and `ConsoleUI#registerSale` (`new Sale(...)` with the identifier built as `"SALE-" + System.currentTimeMillis()`). In contrast, `ConsoleUI#registerController`, `#registerCable` and `#registerMemory` pass raw data to `accessoryService`, which creates the entity. Lines: <fill in>. |
| Explanation | The UI only captures raw input. The domain entities should be created by the component that records them (`ProductService`, `PersonService`, `SaleService`), as the accessories module already does. The code applies two different criteria. |
| Consequence | The UI depends on concrete model classes and knows how to build them; a change in a constructor or in the identifier format forces editing the UI. |
| Proposed solution | Services receive the raw data and create the entity, with the same values and the same identifier format. For sales, `SaleService` creates the `Sale` and returns it so the UI can print the receipt. Behavior unchanged. |
| Other member's code | Yes: Developer 1 (`ProductService`) and Developer 2 (`PersonService`) for the new registration methods. The `Sale` part is the Leader's. |

## 3. Controller
Verdict: violation (V-07)

### V-07

| Field | Content |
|---|---|
| ID | V-07 |
| Pattern | Controller (guiding); Information Expert |
| Module | Sales (Taller 1), Accessories (Req. 1), Exceptions (Req. 6) |
| Evidence | (a) `ConsoleUI#registerSale` generates the sale identifier. (b) `ConsoleUI#listCustomers`, `#listSellers`, `#selectCustomer`, `#selectSeller`, `#selectCompatibleConsoles`, `#findConsoleById` and `#listCompatibleAccessories` filter by type with `instanceof Customer/Seller/Console`. (c) `ConsoleUI#selectProducts` resolves product vs accessory using its own `findProductById` and `accessoryService.findById`, duplicating the lookups of `SaleService`. Lines: <fill in>. |
| Explanation | The UI controller should receive events and delegate. Here it also takes decisions that belong to the services that own the data. The extended-warranty eligibility check is registered under V-04 to avoid duplication. |
| Consequence | Business rules live in two layers (UI and service) and can diverge; the UI cannot be changed without understanding domain rules. |
| Proposed solution | Add query operations to the services (list customers, find customer/seller, find a product or accessory by identifier) and call them from the UI. Same results. |
| Other member's code | Yes: Developer 2 (`PersonService`, `AccessoryService`) and Developer 1 (`ProductService`) for the new query methods. |

## 4. Low Coupling
Verdict: no independent violation so far; pending Main. `ConsoleUI` does not depend on persistence and receives its services by constructor. Its dependency on concrete model classes is covered by V-06.

## 5. High Cohesion
Verdict: violation (V-01, V-02, V-08)

### V-01

| Field | Content |
|---|---|
| ID | V-01 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales (Taller 1), modified by Promotions (Req. 2) and Warranties (Req. 4) |
| Evidence | `com.gamezone.model.Sale#generateReceipt()` builds the full Spanish receipt text (`"Recibo de venta\n"`, `"Subtotal: $"`, `"Descuento aplicado ("`, `"Costo garantías extendidas: $"`, `"Total final: $"`) using a `StringBuilder`. Callers: `ConsoleUI#registerSale` and `ConsoleUI#showSaleDetail`. Lines: <fill in from IDE>. |
| Explanation | `Sale` is a domain entity responsible for its data and totals. Formatting user-facing text is a presentation responsibility, so the class has two unrelated reasons to change. |
| Consequence | Any change to the receipt layout, wording or language forces editing the domain entity. Adding a new receipt line risks touching the totals logic. |
| Proposed solution | Create a `ReceiptFormatter` (Pure Fabrication) that receives a `Sale` and returns the same text; `Sale` keeps only data and calculations. Output must be byte-identical. |
| Other member's code | No. All affected code (`Sale`, `ConsoleUI`) belongs to the Leader. |

### V-02

| Field | Content |
|---|---|
| ID | V-02 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales (Taller 1), extended by Promotions (Req. 2) and Exceptions (Req. 6) |
| Evidence | `com.gamezone.persistence.SaleRepository`: file access in `saveAll`/`loadAll` (`Files.write`, `Files.readAllLines`) together with CSV conversion in `convertToCsv`, `convertFromCsv`, `convertProductsToCsv`, `convertProductsFromCsv`, `convertProductToCsv`, `convertProductFromCsv`. Lines: <fill in>. |
| Explanation | The repository has two reasons to change: how data is stored (path, I/O, error handling) and how entities are encoded (CSV format, field order, separators). It also encodes four entity types (Sale, Customer, Seller, Product). |
| Consequence | Adding a field to Sale (as promotions did with the promotion name and discount) forces editing the I/O class; the CSV logic cannot be reused or tested without touching files. |
| Proposed solution | Extract `SaleCsvMapper` (Pure Fabrication) in `com.gamezone.persistence`; the repository keeps only file I/O and delegates conversion. File content must remain byte-identical. |
| Other member's code | <to verify: check whether ProductRepository duplicates the product CSV format> |

### V-08

| Field | Content |
|---|---|
| ID | V-08 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales, Accessories, Promotions, Returns, Warranties and Exceptions (UI integration of every module) |
| Evidence | `ConsoleUI` contains the menus and handlers of seven modules (`showProductMenu`, `showPersonMenu`, `showSaleMenu`, `showAccessoryMenu`, `showWarrantyMenu`, `showReturnMenu`, `showPromotionMenu` and their private methods). The same four-block `catch` (ResourceNotFoundException, BusinessRuleException, InvalidDataException, PersistenceException, plus GameZoneException) is duplicated in `start()` and `showAccessoryMenu()`. Lines: <fill in>. |
| Explanation | One class has one reason to change per module, plus the exception-to-message translation. It is a large class with unrelated responsibilities. |
| Consequence | Any change in one module touches the same file as the others (merge conflicts in a shared branch); a message change in the exception translation must be repeated in every duplicated block. |
| Proposed solution | One menu class per module, coordinated by `ConsoleUI` (which stays the entry controller), and a single shared exception handler (Pure Fabrication) used by both loops. Identical user messages. |
| Other member's code | No. `ConsoleUI` belongs to the Leader. |

## 6. Polymorphism
Verdict: violation (V-03, V-04)

### V-03

| Field | Content |
|---|---|
| ID | V-03 |
| Pattern | Polymorphism (guiding); Protected Variations |
| Module | Sales (Taller 1) |
| Evidence | `SaleRepository#convertProductToCsv` uses `instanceof VideoGame` / `instanceof Console`; `SaleRepository#convertProductFromCsv` uses `type.equals("VIDEO_GAME")` / `type.equals("CONSOLE")`. Lines: <fill in>. |
| Explanation | Behavior variants by product type are resolved with type conditionals instead of polymorphism. |
| Consequence | Any new product type must be added in both methods, otherwise a `PersistenceException` ("Unsupported product type") is thrown at runtime. |
| Proposed solution | After V-02, replace the conditionals with a per-type serializer (small interface, one implementation per product type, selected through a lookup), keeping the same CSV output. |
| Other member's code | <to verify> |

### V-04

| Field | Content |
|---|---|
| ID | V-04 |
| Pattern | Polymorphism (guiding); Protected Variations |
| Module | Sales (Taller 1), Accessories (Req. 1), Warranties (Req. 4) |
| Evidence | `SaleService#registerSale` uses `soldProduct instanceof Accessory` in the stock validation loop and again in the stock update loop. `SaleService#assignWarranties` uses `if (!(product instanceof Console)) continue;`. The same warranty eligibility rule is repeated in `ConsoleUI#askExtendedWarranties` (`if (!(product instanceof Console)) continue;`). Lines: <fill in>. |
| Explanation | Behavior that varies by product subtype (which stock store applies, whether a warranty applies) is decided by type checks in the service and in the UI instead of being resolved by the product hierarchy. |
| Consequence | Each new product category (as `Accessory` in Req. 1) requires editing several places; forgetting one gives wrong stock or warranty handling at runtime, and the UI and the service could disagree on which products have a warranty. |
| Proposed solution | Move the variation behind a polymorphic operation on `Product` (e.g. warranty eligibility) and an intermediary for stock handling per product type. Behavior unchanged. |
| Other member's code | Yes: Developer 1 (`Product`, `Accessory`, `Console` hierarchy), possibly Developer 2 (`AccessoryService`). To settle in consolidation. Same root cause as V-03 (`SaleRepository`), different code point. |

## 7. Pure Fabrication
Verdict: see V-01, V-02 and V-08 (solution pattern)

## 8. Indirection
Verdict: correct so far (`ConsoleUI` only talks to services; `SaleService` reaches persistence through `SaleRepository`). Pending Main and restoreStock.

## 9. Protected Variations
Verdict: related to V-03, V-04 and V-08 (pending until all classes are reviewed)

## Functional defects observed (out of scope, for the consolidated audit)

To confirm by running the program before consolidating.

- F-01: a sale that includes an accessory may fail when saving, after the stock was already updated (`SaleRepository#convertProductToCsv` only supports `VideoGame` and `Console`).
- F-02: `extendedWarrantyCost` is not written by `SaleRepository#convertToCsv`, so a reloaded sale may show a different total.
- F-03: `ConsoleUI#registerController`, `#registerCable` and `#registerMemory` parse numbers outside any `try`; an invalid number may end the program with a stack trace.
- F-04: `Sale#calculateTotal()` returns the subtotal; check whether anything uses it as the sale total.