# Leader Technical Audit: GRASP

Audited commit of `develop`: 82c1a98f4bc1f8672f232e812dadb4687d978882

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
Verdict: <pending until all classes are reviewed>

## 2. Creator
Verdict: <pending>

## 3. Controller
Verdict: <pending>

## 4. Low Coupling
Verdict: <pending>

## 5. High Cohesion
Verdict: violation

### V-01

| Field | Content |
|---|---|
| ID | V-01 |
| Pattern | High Cohesion (guiding); Pure Fabrication (solution) |
| Module | Sales (Taller 1), modified by Promotions (Req. 2) and Warranties (Req. 4) |
| Evidence | `com.gamezone.model.Sale#generateReceipt()` builds the full Spanish receipt text (`"Recibo de venta\n"`, `"Subtotal: $"`, `"Descuento aplicado ("`, `"Costo garantías extendidas: $"`, `"Total final: $"`) using a `StringBuilder`. Lines: <fill in from IDE>. |
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
| Evidence | `com.gamezone.persistence.SaleRepository`: file access in `saveAll`/`loadAll` (`Files.write`, `Files.readAllLines`) together with CSV conversion in `convertToCsv`, `convertFromCsv`, `convertProductsToCsv`, `convertProductsFromCsv`, `convertProductToCsv`, `convertProductFromCsv`. Lines: <fill in>. |
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
| Evidence | `SaleRepository#convertProductToCsv` uses `instanceof VideoGame` / `instanceof Console`; `SaleRepository#convertProductFromCsv` uses `type.equals("VIDEO_GAME")` / `type.equals("CONSOLE")`. Lines: <fill in>. |
| Explanation | Behavior variants by product type are resolved with type conditionals instead of polymorphism. |
| Consequence | Any new product type must be added in both methods, otherwise a `PersistenceException` ("Unsupported product type") is thrown at runtime. |
| Proposed solution | After V-02, replace the conditionals with a per-type serializer (small interface, one implementation per product type, selected through a lookup), keeping the same CSV output. |
| Other member's code | <to verify> |

## 7. Pure Fabrication
Verdict: see V-01 and V-02 (solution pattern)

## 8. Indirection
Verdict: <pending>

## 9. Protected Variations
Verdict: related to V-03 (pending until all classes are reviewed)