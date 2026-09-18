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
    %% PROMOTION HIERARCHY
    %% =========================

    class Promotion {
        <<abstract>>
        -String identifier
        -String name
        -LocalDate startDate
        -LocalDate endDate
        -boolean active
        +Promotion(String identifier, String name, LocalDate startDate, LocalDate endDate)
        +String getIdentifier()
        +String getName()
        +LocalDate getStartDate()
        +LocalDate getEndDate()
        +boolean isActive()
        +void setActive(boolean active)
        +abstract double calculateDiscount(Sale sale)
    }

    class PercentageDiscount {
        -double percentage
        +PercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double percentage)
        +double getPercentage()
        +void setPercentage(double percentage)
        +double calculateDiscount(Sale sale)
    }

    class CategoryDiscount {
        -String category
        -double percentage
        +CategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, String category, double percentage)
        +String getCategory()
        +double getPercentage()
        +void setCategory(String category)
        +void setPercentage(double percentage)
        +double calculateDiscount(Sale sale)
    }

    class BulkPurchaseDiscount {
        -int minimumQuantity
        -double percentage
        +BulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double percentage)
        +int getMinimumQuantity()
        +double getPercentage()
        +void setMinimumQuantity(int minimumQuantity)
        +void setPercentage(double percentage)
        +double calculateDiscount(Sale sale)
    }

    %% =========================
    %% SALE
    %% =========================

    class Sale {
        -LocalDate date
        -List~Product~ products
        -String appliedPromotionName
        -double discountAmount
        +void addProduct(Product product)
        +List~Product~ getProducts()
        +double calculateTotal()
        +String getAppliedPromotionName()
        +double getDiscountAmount()
        +void applyPromotion(Promotion promotion)
    }

    %% =========================
    %% PROMOTION PERSISTENCE
    %% =========================

    class PromotionRepository {
        -String FILE_PATH
        +PromotionRepository()
        +List~Promotion~ loadAll()
        +void saveAll(List~Promotion~ promotions)
    }

    %% =========================
    %% PROMOTION SERVICE
    %% =========================

    class PromotionService {
        -PromotionRepository promotionRepository
        +PromotionService(PromotionRepository promotionRepository)
        +void registerPromotion(Promotion promotion)
        +List~Promotion~ listAllPromotions()
        +Promotion findBestPromotionFor(Sale sale)
    }

    %% =========================
    %% SALE SERVICE
    %% =========================

    class SaleService {
        -PromotionService promotionService
        +SaleService(PromotionService promotionService)
        +void registerSale(Sale sale)
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    Sale "1" --> "1..*" Product : contains

    PromotionService --> PromotionRepository : uses
    PromotionService --> Promotion : manages

    Promotion --> Sale : calculates discount

    Sale --> Promotion : applies

    SaleService --> Sale : registers
    SaleService --> PromotionService : finds best promotion