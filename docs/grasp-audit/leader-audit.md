# Leader Technical Audit: GRASP

Audited commit of `develop`: 82c1a98f4bc1f8672f232e812dadb4687d978882
## Scope

In the repository the console menu class is named `ConsoleUI` (called `ConsoleMenu` in the assignment).

| Module | Class / method audited |
|---|---|
| Sales | Sale, SaleRepository, SaleService, ConsoleUI, Main |
| Accessories | Integration in SaleService and ConsoleUI |
| Promotions | Discount application in SaleService.registerSale; changes in Sale (applied discount, generateReceipt); integration in ConsoleUI |
| Returns | ProductService.restoreStock; integration in ConsoleUI |
| Warranties | Warranty assignment and extended warranty cost in SaleService.registerSale; integration in ConsoleUI |
| Exceptions | Differentiated exception handling in ConsoleUI |

Not audited by the Leader: `Sale.canBeReturned()` (Developer 1) and every other method of `ProductService` (Developer 1).

## 1. Information Expert
Verdict: violation (V-05)

### V-05

| Field | Content |
|---|---|
| ID | V-05 |
| Pattern | Information Expert |
| Module | Sales, Accessories, Returns |
| Evidence | `SaleService#registerSale` computes `storedAccessory.getAvailableQuantity() - 1` and `storedProduct.getAvailableQuantity() - 1`, then calls `accessoryService.updateStock(id, newQuantity)` / `productService.updateStock(product, newQuantity)`. `ProductService#restoreStock` computes `product.getAvailableQuantity() + quantity` and then calls `updateStock(product, newQuantity)`. Private helpers `findProductById` and `findAccessoryById` in `SaleService` search the lists returned by `productService.listProducts()` and `accessoryService.listAllAccessories()`. Lines: 81 - 91. |
| Explanation | The stock data belongs to `Product`/`Accessory`, which only expose a getter and a setter. The arithmetic (subtract on sale, add on return) is done outside the owner, in two different services, and the lookups duplicate what `ProductService` and `AccessoryService` already provide. |
| Consequence | Any change in the stock rules must be replicated in `SaleService` and in `restoreStock`, and kept consistent with the accessory stock update. |
| Proposed solution | Give the owner the operations (increase and decrease stock on `Product`, inherited by accessories) and let the services call them and persist; reuse the lookups of the product and accessory services. Same stock results. |
| Other member's code | Yes: Developer 1 (`Product`, `ProductService#updateStock`) and Developer 2 (`AccessoryService#updateStock`). The `SaleService` and `restoreStock` parts are the Leader's. To settle in consolidation. |

## 2. Creator
Verdict: violation (V-06). `Main`, `SaleService` and `SaleRepository` are correct: `Main` is the composition root and creates repositories and services; `SaleService` delegates warranty creation to `WarrantyService`.

### V-06

| Field | Content |
|---|---|
| ID | V-06 |
| Pattern | Creator |
| Module | Sales, Products and Persons (integration in the UI) |
| Evidence | `ConsoleUI#registerVideoGame` (`new VideoGame(...)`), `ConsoleUI#registerConsole` (`new Console(...)`), `ConsoleUI#registerCustomer` (`new Customer(...)`) and `ConsoleUI#registerSale` (`new Sale(...)` with the identifier built as `"SALE-" + System.currentTimeMillis()`). In contrast, `ConsoleUI#registerController`, `#registerCable` and `#registerMemory` pass raw data to `accessoryService`, which creates the entity. Lines: 152 - 343, 979 - 991. |
| Explanation | The UI only captures raw input. The domain entities should be created by the component that records them (`ProductService`, `PersonService`, `SaleService`), as the accessories module already does. The code applies two different criteria. |
| Consequence | The UI depends on concrete model classes and knows how to build them; a change in a constructor or in the identifier format forces editing the UI. |
| Proposed solution | Services receive the raw data and create the entity, with the same values and the same identifier format. For sales, `SaleService` creates the `Sale` and returns it so the UI can print the receipt. Behavior unchanged. |
| Other member's code | Yes: Developer 1 (`ProductService`) and Developer 2 (`PersonService`) for the new registration methods. The `Sale` part is the Leader's. |

## 3. Controller
Verdict: violation (V-07). `Main` only starts the UI, so the pattern does not apply to it.

### V-07

| Field | Content |
|---|---|
| ID | V-07 |
| Pattern | Controller (guiding); Information Expert |
| Module | Sales, Accessories, Exceptions |
| Evidence | (a) `ConsoleUI#registerSale` generates the sale identifier. (b) `ConsoleUI#listCustomers`, `#listSellers`, `#selectCustomer`, `#selectSeller`, `#selectCompatibleConsoles`, `#findConsoleById` and `#listCompatibleAccessories` filter by type with `instanceof Customer/Seller/Console`. (c) `ConsoleUI#selectProducts` resolves product vs accessory using its own `findProductById` and `accessoryService.findById`, duplicating the lookups of `SaleService`. Lines: 335. 247 - 261, 263 - 277, 362 - 385 . |
| Explanation | The UI controller should receive events and delegate. Here it also takes decisions that belong to the services that own the data. The extended-warranty eligibility check is registered under V-04 to avoid duplication. |
| Consequence | Business rules live in two layers (UI and service) and can diverge; the UI cannot be changed without understanding domain rules. |
| Proposed solution | Add query operations to the services (list customers, find customer/seller, find a product or accessory by identifier) and call them from the UI. Same results. |
| Other member's code | Yes: Developer 2 (`PersonService`, `AccessoryService`) and Developer 1 (`ProductService`) for the new query methods. |

## 4. Low Coupling
Verdict: correct in the Leader's code. `ConsoleUI` does not depend on persistence and receives its services by constructor; its dependency on concrete model classes is covered by V-06. One boundary candidate outside the Leader's code is reported in the last section (B-01).

## 5. High Cohesion
Verdict: violation (V-01, V-02, V-08, V-09)

### V-01

| Field | Content |
|---|---|
| ID | V-01 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales, modified by Promotions and Warranties |
| Evidence | `com.gamezone.model.Sale#generateReceipt()` builds the full Spanish receipt text (`"Recibo de venta\n"`, `"Subtotal: $"`, `"Descuento aplicado ("`, `"Costo garantías extendidas: $"`, `"Total final: $"`) using a `StringBuilder`. Callers: `ConsoleUI#registerSale` and `ConsoleUI#showSaleDetail`. Lines: 147 - 175. |
| Explanation | `Sale` is a domain entity responsible for its data and totals. Formatting user-facing text is a presentation responsibility, so the class has two unrelated reasons to change. |
| Consequence | Any change to the receipt layout, wording or language forces editing the domain entity. Adding a new receipt line risks touching the totals logic. |
| Proposed solution | Create a `ReceiptFormatter` (Pure Fabrication) that receives a `Sale` and returns the same text; `Sale` keeps only data and calculations. Output must be byte-identical. |
| Other member's code | No. All affected code (`Sale`, `ConsoleUI`) belongs to the Leader. |

### V-02

| Field | Content |
|---|---|
| ID | V-02 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales, extended by Promotions and Exceptions |
| Evidence | `com.gamezone.persistence.SaleRepository`: file access in `saveAll`/`loadAll` (`Files.write`, `Files.readAllLines`) together with CSV conversion in `convertToCsv`, `convertFromCsv`, `convertProductsToCsv`, `convertProductsFromCsv`, `convertProductToCsv`, `convertProductFromCsv`. Lines:55 - 243. |
| Explanation | The repository has two reasons to change: how data is stored (path, I/O, error handling) and how entities are encoded (CSV format, field order, separators). It also encodes four entity types (Sale, Customer, Seller, Product). |
| Consequence | Adding a field to Sale (as promotions did with the promotion name and discount) forces editing the I/O class; the CSV logic cannot be reused or tested without touching files. |
| Proposed solution | Extract `SaleCsvMapper` (Pure Fabrication) in `com.gamezone.persistence`; the repository keeps only file I/O and delegates conversion. File content must remain byte-identical. |
| Other member's code |

### V-08

| Field | Content |
|---|---|
| ID | V-08 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales, Accessories, Promotions, Returns, Warranties and Exceptions (UI integration of every module) |
| Evidence | `ConsoleUI` contains the menus and handlers of seven modules (`showProductMenu`, `showPersonMenu`, `showSaleMenu`, `showAccessoryMenu`, `showWarrantyMenu`, `showReturnMenu`, `showPromotionMenu` and their private methods). The same block of catches (ResourceNotFoundException, BusinessRuleException, InvalidDataException, PersistenceException, GameZoneException) is duplicated in `start()` and `showAccessoryMenu()`. Lines: 1 - 1222. |
| Explanation | One class has one reason to change per module, plus the exception-to-message translation. It is a large class with unrelated responsibilities. |
| Consequence | Any change in one module touches the same file as the others (merge conflicts in a shared branch); a message change in the exception translation must be repeated in every duplicated block. |
| Proposed solution | One menu class per module, coordinated by `ConsoleUI` (which stays the entry controller), and a single shared exception handler (Pure Fabrication) used by both loops. Identical user messages. |
| Other member's code | No. `ConsoleUI` belongs to the Leader. |

### V-09

| Field | Content |
|---|---|
| ID | V-09 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Startup |
| Evidence | `com.gamezone.Main#main` assembles repositories, services and the UI, while `Main#preloadSellers` decides whether initial data is needed (loop with `instanceof Seller` over `personService.getAllPeople()`) and creates three `Seller` objects. Lines:46 - 63, 50. |
| Explanation | `Main` has two reasons to change: the wiring of the application and the initial data set. Detecting whether sellers exist also duplicates a type filter that belongs to the person service. |
| Consequence | Changing the preloaded sellers requires editing the application entry point. Severity: low. |
| Proposed solution | Move the preload into a `DataInitializer` (Pure Fabrication) called from `Main`, with the seller check delegated to a query of `PersonService`. Same sellers, same condition. |
| Other member's code | Yes, a small part: Developer 2 (`PersonService`) for the query used by the check. |

## 6. Polymorphism
Verdict: violation (V-03, V-04)

### V-03

| Field | Content |
|---|---|
| ID | V-03 |
| Pattern | Polymorphism (guiding); Protected Variations |
| Module | Sales |
| Evidence | `SaleRepository#convertProductToCsv` uses `instanceof VideoGame` / `instanceof Console`; `SaleRepository#convertProductFromCsv` uses `type.equals("VIDEO_GAME")` / `type.equals("CONSOLE")`. Lines: 187 - 213, 222 - 243. |
| Explanation | Behavior variants by product type are resolved with type conditionals instead of polymorphism. |
| Consequence | Any new product type must be added in both methods, otherwise a `PersistenceException` ("Unsupported product type") is thrown at runtime. |
| Proposed solution | After V-02, replace the conditionals with a per-type serializer (small interface, one implementation per product type, selected through a lookup), keeping the same CSV output. |
| Other member's code | 

### V-04

| Field | Content |
|---|---|
| ID | V-04 |
| Pattern | Polymorphism (guiding); Protected Variations |
| Module | Sales, Accessories, Warranties |
| Evidence | `SaleService#registerSale` uses `soldProduct instanceof Accessory` in the stock validation loop and again in the stock update loop. `SaleService#assignWarranties` uses `if (!(product instanceof Console)) continue;`. The same warranty eligibility rule is repeated in `ConsoleUI#askExtendedWarranties` (`if (!(product instanceof Console)) continue;`). Lines: 82 - 90, 129 - 132. |
| Explanation | Behavior that varies by product subtype (which stock store applies, whether a warranty applies) is decided by type checks in the service and in the UI instead of being resolved by the product hierarchy. |
| Consequence | Each new product category (as `Accessory` did) requires editing several places; forgetting one gives wrong stock or warranty handling at runtime, and the UI and the service could disagree on which products have a warranty. |
| Proposed solution | Move the variation behind a polymorphic operation on `Product` (e.g. warranty eligibility) and an intermediary for stock handling per product type. Behavior unchanged. |
| Other member's code | Yes: Developer 1 (`Product`, `Accessory`, `Console` hierarchy), possibly Developer 2 (`AccessoryService`). To settle in consolidation. Same root cause as V-03 (`SaleRepository`), different code point. |

## 7. Pure Fabrication
Verdict: not a violation by itself; it is the solution pattern of V-01, V-02, V-08 and V-09.

## 8. Indirection
Verdict: correct. `ConsoleUI` only talks to services; services reach persistence through repositories; `Main` wires everything by constructor. Services depend on concrete repository classes (no interfaces); this is not registered as a violation because no foreseeable change in storage justifies it and fixing it would affect the code of all three members.

## 9. Protected Variations
Verdict: related to V-03, V-04 and V-08 (no independent finding). A new product category or a new module forces edits in several classes.

## Findings reported to the consolidation (outside the Leader's code, not audited here)

- B-01 (candidate, Developer 2): in `Main`, `new ReturnRepository(saleService, productService, accessoryService)` shows a persistence-layer class receiving service-layer objects, which inverts the dependency direction `ui → service → persistence → model`; `ReturnRepository` is created after the services in `Main` for this reason. Possible Low Coupling violation in `ReturnRepository`. Developer 2 must confirm it in their report.
- B-02 (to verify, Developer 2): `new WarrantyService(warrantyRepository, saleRepository, productService)` shows `WarrantyService` using `SaleRepository` directly instead of `SaleService`. The `SaleRepository` side is the Leader's code.

