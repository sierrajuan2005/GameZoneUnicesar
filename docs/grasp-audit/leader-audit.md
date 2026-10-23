# Leader Technical Audit: GRASP

Audited commit of `develop`: <paste hash from git log -1 --format=%H>

## Scope

| Module (requirement) | Class / method audited |
|---|---|
| Sales  | Sale, SaleRepository, SaleService, ConsoleMenu, Main |
| Accessories  | Integration in SaleService and ConsoleMenu |
| Promotions  | Discount application in SaleService.registerSale; changes in Sale (applied discount, generateReceipt); integration in ConsoleMenu |
| Returns  | ProductService.restoreStock; integration in ConsoleMenu |
| Warranties  | Warranty assignment and extended warranty cost in SaleService.registerSale; integration in ConsoleMenu |
| Exceptions  | Differentiated exception handling in ConsoleMenu |

Not audited by the Leader: `Sale.canBeReturned()` (Developer 1, Req. 3).

## 1. Information Expert
Verdict: violation (pending ConsoleMenu, Main and restoreStock)

### V-05

| Field | Content |
|---|---|
| ID | V-05 |
| Pattern | Information Expert |
| Module | Sales , Accessories  |
| Evidence | `SaleService#registerSale` computes `storedAccessory.getAvailableQuantity() - 1` and `storedProduct.getAvailableQuantity() - 1`, then calls `accessoryService.updateStock(id, newQuantity)` / `productService.updateStock(product, newQuantity)`. Private helpers `findProductById` and `findAccessoryById` search the lists returned by `productService.listProducts()` and `accessoryService.listAllAccessories()`. Lines: 81 -91 ,  . |
| Explanation | The stock data belongs to `Product`/`Accessory` and the lookup to `ProductService`/`AccessoryService`. `SaleService` reads their data, computes the new value and writes it back, instead of asking the owner to perform the operation. The two services also expose different update signatures. |
| Consequence | Any change in stock rules must be replicated in `SaleService` and kept consistent with `ProductService.restoreStock` (Req. 3). |
| Proposed solution | Ask the owners: a "reduce stock" operation and a lookup in `ProductService`/`AccessoryService` (counterpart of `restoreStock`), used by `SaleService`. Same stock results. |
| Other member's code | Yes: Developer 1 (`Product`, `ProductService`) and Developer 2 (`Accessory`, `AccessoryService`). To settle in consolidation. |

## 2. Creator
Verdict: correct so far in SaleService and SaleRepository; pending ConsoleMenu and Main (who creates Sale)

## 3. Controller
Verdict: <pending>

## 4. Low Coupling
Verdict: <pending>. SaleService has no independent violation; its knowledge of concrete product types is covered by V-04.

## 5. High Cohesion
Verdict: violation

### V-01

| Field | Content |
|---|---|
| ID | V-01 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales (Taller 1), modified by Promotions (Req. 2) and Warranties (Req. 4) |
| Evidence | `com.gamezone.model.Sale#generateReceipt()` builds the full Spanish receipt text (`"Recibo de venta\n"`, `"Subtotal: $"`, `"Descuento aplicado ("`, `"Costo garantías extendidas: $"`, `"Total final: $"`) using a `StringBuilder`. Lines: 147 - 175 |
| Explanation | `Sale` is a domain entity responsible for its data and totals. Formatting user-facing text is a presentation responsibility, so the class has two unrelated reasons to change. |
| Consequence | Any change to the receipt layout, wording or language forces editing the domain entity. Adding a new receipt line risks touching the totals logic. |
| Proposed solution | Create a `ReceiptFormatter` (Pure Fabrication) that receives a `Sale` and returns the same text; `Sale` keeps only data and calculations. Output must be byte-identical. |
| Other member's code | No. All affected code (`Sale`, its callers in `SaleService`/`ConsoleMenu`) belongs to the Leader. |

### V-02

| Field | Content |
|---|---|
| ID | V-02 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales (Taller 1), extended by Promotions (Req. 2) and Exceptions (Req. 6) |
| Evidence | `com.gamezone.persistence.SaleRepository`: file access in `saveAll`/`loadAll` (`Files.write`, `Files.readAllLines`) together with CSV conversion in `convertToCsv`, `convertFromCsv`, `convertProductsToCsv`, `convertProductsFromCsv`, `convertProductToCsv`, `convertProductFromCsv`. Lines: 55 - 243. |
| Explanation | The repository has two reasons to change: how data is stored (path, I/O, error handling) and how entities are encoded (CSV format, field order, separators). It also encodes four entity types (Sale, Customer, Seller, Product). |
| Consequence | Adding a field to Sale (as promotions did with the promotion name and discount) forces editing the I/O class; the CSV logic cannot be reused or tested without touching files. |
| Proposed solution | Extract `SaleCsvMapper` (Pure Fabrication) in `com.gamezone.persistence`; the repository keeps only file I/O and delegates conversion. File content must remain byte-identical. |
| Other member's code | <to verify: check whether ProductRepository duplicates the product CSV format> |

## 6. Polymorphism
Verdict: violation

### V-03

| Field | Content |
|---|---|
| ID | V-03 |
| Pattern | Polymorphism (guiding); Protected Variations |
| Module | Sales (Taller 1) |
| Evidence | `SaleRepository#convertProductToCsv` uses `instanceof VideoGame` / `instanceof Console`; `SaleRepository#convertProductFromCsv` uses `type.equals("VIDEO_GAME")` / `type.equals("CONSOLE")`. Lines: 188 - 197, 226 - 238. |
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
| Evidence | `SaleService#registerSale` uses `soldProduct instanceof Accessory` in the stock validation loop and again in the stock update loop. `SaleService#assignWarranties` uses `if (!(product instanceof Console)) continue;`. Lines: 66 - 72, 130 - 132 . |
| Explanation | Behavior that varies by product subtype (which stock store applies, whether a warranty applies) is decided by type checks in the service instead of being resolved by the product hierarchy. |
| Consequence | Each new product category (as `Accessory` in Req. 1) requires editing three places in `SaleService`; forgetting one gives wrong stock or warranty handling at runtime. |
| Proposed solution | Move the variation behind a polymorphic operation on `Product` (e.g. warranty eligibility) and an intermediary for stock handling per product type. Behavior unchanged. |
| Other member's code | Yes: Developer 1 (`Product`, `Accessory`, `Console` hierarchy), possibly Developer 2 (`AccessoryService`). To settle in consolidation. Same root cause as V-03 (`SaleRepository`), different code point. |

## 7. Pure Fabrication
Verdict: see V-01 and V-02 (solution pattern)

## 8. Indirection
Verdict: <pending>. SaleService so far correct: persistence, promotions and warranties are behind repository and services.

## 9. Protected Variations
Verdict: related to V-03 and V-04 (pending until all classes are reviewed)