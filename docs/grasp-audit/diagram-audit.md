# GRASP Class Diagrams - GameZone Unicesar

This document contains the class diagrams representing the system architecture **Before** refactoring (reflecting the audit findings) and **After** applying the GRASP design corrections for Phase 2.

## 1. Class Diagram: BEFORE (Phase 1 / Audit Baseline)

The following diagram illustrates the system architecture at the start of Phase 2, highlighting the classes involved in the consolidated audit findings (V-01 to V-17).

```mermaid
classDiagram
    class Main {
        +main(String[] args)
        -preloadSellers()
    }
    class ConsoleUI {
        +start()
        +registerVideoGame()
        +registerConsole()
        +registerCustomer()
        +registerSale()
        +listCustomers()
        +listSellers()
        +selectCustomer()
        +selectSeller()
        +selectCompatibleConsoles()
        +findConsoleById()
        +listCompatibleAccessories()
        +selectProducts()
        +askExtendedWarranties()
        +showAccessoryMenu()
    }
    class Sale {
        +generateReceipt() String
        +canBeReturned() boolean
        +calculateRefundAmount() double
    }
    class SaleRepository {
        +saveAll()
        +loadAll()
        +convertToCsv()
        +convertFromCsv()
        +convertProductsToCsv()
        +convertProductsFromCsv()
        +convertProductToCsv()
        +convertProductFromCsv()
    }
    class SaleService {
        +registerSale()
        +processSale()
        +assignWarranties()
        +findProductById()
        +findAccessoryById()
    }
    class ProductService {
        +restoreStock()
        +findByIdentifier() Product
    }
    class CategoryDiscount {
        +calculateDiscount() double
    }
    class AccessoryService {
        +createAccessory()
    }
    class ReturnRepository {
        +saveAll()
        +loadAll()
    }
    class ReturnService {
        +processReturn()
    }
    class WarrantyService {
        -warrantiesList: List
        +registerWarranty()
    }
    class WarrantyRepository {
        +saveAll()
    }
    class AccessoryRepository {
        +saveAccessory()
    }
    class PromotionRepository {
        +savePromotion()
    }

    Main --> ConsoleUI : V-09
    Main --> SaleRepository
    Main --> ReturnRepository
    ConsoleUI --> SaleService : V-06, V-07
    ConsoleUI --> ProductService : V-07
    ConsoleUI --> AccessoryService
    SaleService --> SaleRepository
    SaleService --> ProductService : V-04, V-05
    ReturnRepository --> SaleService : V-13
    ReturnRepository --> ProductService : V-13
    AccessoryRepository --> AccessoryRepository : V-16
    PromotionRepository --> PromotionRepository : V-16
    CategoryDiscount --> Product : V-10
    Sale --> Sale : V-01
    SaleRepository --> SaleRepository : V-02, V-03
    ConsoleUI --> ConsoleUI : V-08
    AccessoryService --> AccessoryService : V-12
    WarrantyService --> WarrantyService : V-15
    ReturnService --> ReturnService : V-17

    note for Sale "V-01: Receipt formatting inside Sale"
    note for SaleRepository "V-02: File I/O + CSV mapping\nV-03: Concrete product type checks (instanceof)"
    note for SaleService "V-04: Product-type conditionals\nV-05: Duplicated stock arithmetic/lookups\nV-14: Combined responsibilities in processSale"
    note for ConsoleUI "V-06: Direct entity creation\nV-07: Domain lookups & filtering\nV-08: Monolithic menu & exception handling"
    note for Main "V-09: Assembly + Seller preloading"
    note for CategoryDiscount "V-10: Concrete product class checks"
    note for ProductService "V-11: Returns null instead of exception"
    note for AccessoryService "V-12: Direct instantiation of subtypes"
    note for ReturnRepository "V-13: Direct dependency on services"
    note for WarrantyService "V-15: Duplicated repository data in internal list"
    note for AccessoryRepository "V-16: Type conditionals for serialization"
    note for ReturnService "V-17: Combined refund calculation & stock restoration"