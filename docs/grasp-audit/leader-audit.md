# Leader Technical Audit: GRASP


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
Verdict: violation (so far)

### V-01
(paste the V-01 table from my previous message)

## 6. Polymorphism
Verdict: <pending>

## 7. Pure Fabrication
Verdict: see V-01 (solution pattern)

## 8. Indirection
Verdict: <pending>

## 9. Protected Variations
Verdict: <pending>