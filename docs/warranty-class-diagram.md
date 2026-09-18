classDiagram

    %% =========================
    %% PRODUCT
    %% =========================

    class Product {
        <<abstract>>
        -String identifier
        -String title
        -double price
        -int availableQuantity
        +String getIdentifier()
        +String getTitle()
        +double getPrice()
        +int getAvailableQuantity()
        +String getDescription()
    }

    %% =========================
    %% WARRANTY HIERARCHY
    %% =========================

    class Warranty {
        <<abstract>>
        -String identifier
        -String name
        -int durationMonths
        -String coverage
        +Warranty(String identifier, String name, int durationMonths, String coverage)
        +String getIdentifier()
        +String getName()
        +int getDurationMonths()
        +String getCoverage()
        +void setName(String name)
        +void setDurationMonths(int durationMonths)
        +void setCoverage(String coverage)
        +abstract boolean isValid()
    }

    class BasicWarranty {
        -boolean coversManufacturingDefects
        +BasicWarranty(String identifier, String name, int durationMonths, String coverage, boolean coversManufacturingDefects)
        +boolean isCoversManufacturingDefects()
        +void setCoversManufacturingDefects(boolean coversManufacturingDefects)
        +boolean isValid()
    }

    class ExtendedWarranty {
        -double additionalCost
        -boolean coversAccidentalDamage
        +ExtendedWarranty(String identifier, String name, int durationMonths, String coverage, double additionalCost, boolean coversAccidentalDamage)
        +double getAdditionalCost()
        +boolean isCoversAccidentalDamage()
        +void setAdditionalCost(double additionalCost)
        +void setCoversAccidentalDamage(boolean coversAccidentalDamage)
        +boolean isValid()
    }

    %% =========================
    %% SALE
    %% =========================

    class Sale {
        -LocalDate date
        -List~Product~ products
        +void addProduct(Product product)
        +List~Product~ getProducts()
        +double calculateTotal()
    }

    %% =========================
    %% PERSISTENCE
    %% =========================

    class WarrantyRepository {
        -String FILE_PATH
        +WarrantyRepository()
        +List~Warranty~ loadAll()
        +void saveAll(List~Warranty~ warranties)
        +Optional~Warranty~ findById(String identifier)
    }

    %% =========================
    %% SERVICE
    %% =========================

    class WarrantyService {
        -WarrantyRepository warrantyRepository
        +WarrantyService(WarrantyRepository warrantyRepository)
        +void registerWarranty(Warranty warranty)
        +List~Warranty~ listAllWarranties()
        +Optional~Warranty~ findWarrantyById(String identifier)
    }

    %% =========================
    %% SALE SERVICE
    %% =========================

    class SaleService {
        -WarrantyService warrantyService
        +SaleService(WarrantyService warrantyService)
        +void registerSale(Sale sale)
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty

    Sale "1" --> "1..*" Product : contains

    Product "1" --> "0..1" Warranty : has warranty

    WarrantyRepository --> Warranty : persists

    WarrantyService --> WarrantyRepository : uses
    WarrantyService --> Warranty : manages

    SaleService --> Sale : registers
    SaleService --> WarrantyService : manages warranties