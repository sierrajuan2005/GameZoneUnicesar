# Diagrama de Clases - Promociones

```mermaid
classDiagram

    class Promotion {
        <<abstract>>
        -String identifier
        -String name
        -LocalDate startDate
        -LocalDate endDate
        +Promotion(String identifier, String name, LocalDate startDate, LocalDate endDate)
        +String getIdentifier()
        +void setIdentifier(String identifier)
        +String getName()
        +void setName(String name)
        +LocalDate getStartDate()
        +void setStartDate(LocalDate startDate)
        +LocalDate getEndDate()
        +void setEndDate(LocalDate endDate)
        +boolean isActive(LocalDate date)
        +double calculateDiscount(Sale sale)
    }

    class PercentageDiscount {
        -double discountPercentage
        +PercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +double calculateDiscount(Sale sale)
    }

    class CategoryDiscount {
        -double discountPercentage
        -String targetCategory
        +CategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +String getTargetCategory()
        +void setTargetCategory(String targetCategory)
        +double calculateDiscount(Sale sale)
    }

    class BulkPurchaseDiscount {
        -int minimumQuantity
        -double discountPercentage
        +BulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double discountPercentage)
        +int getMinimumQuantity()
        +void setMinimumQuantity(int minimumQuantity)
        +double getDiscountPercentage()
        +void setDiscountPercentage(double discountPercentage)
        +double calculateDiscount(Sale sale)
    }

    class PromotionRepository {
        -String FILE_PATH
        -String convertToCsv(Promotion p)
        +void saveAll(List~Promotion~ promotions)
        -Promotion convertFromCsv(String line)
        +List~Promotion~ loadAll()
    }

    class PromotionService {
        -PromotionRepository promotionRepository
        +PromotionService(PromotionRepository promotionRepository)
        +Promotion findById(String identifier)
        -void validateUniqueId(String identifier)
        -void savePromotion(Promotion promotion)
        +void registerPercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage)
        +void registerCategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory)
        +void registerBulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minQuantity, double discountPercentage)
        +List~Promotion~ listAllPromotions()
        +List~Promotion~ listActivePromotions()
        +Promotion findBestPromotionFor(Sale sale)
    }

    class Sale {
        -String identifier
        -LocalDate date
        -Customer customer
        -Seller seller
        -List~Product~ products
        -String appliedPromotionName
        -double discountAmount
        +Sale(String identifier, LocalDate date, Customer customer, Seller seller, List~Product~ products)
        +String getIdentifier()
        +void setIdentifier(String identifier)
        +LocalDate getDate()
        +void setDate(LocalDate date)
        +Customer getCustomer()
        +void setCustomer(Customer customer)
        +Seller getSeller()
        +void setSeller(Seller seller)
        +List~Product~ getProducts()
        +void setProducts(List~Product~ products)
        +String getAppliedPromotionName()
        +void setAppliedPromotionName(String appliedPromotionName)
        +double getDiscountAmount()
        +void setDiscountAmount(double discountAmount)
        +void addProduct(Product product)
        +double calculateTotal()
        +String generateReceipt()
        +boolean canBeReturned()
        +String toString()
    }

    Promotion <|-- PercentageDiscount
    Promotion <|-- CategoryDiscount
    Promotion <|-- BulkPurchaseDiscount

    PromotionRepository --> Promotion : persists

    PromotionService --> PromotionRepository : uses
    PromotionService --> Promotion : manages
    PromotionService ..> Sale : evaluates

    Promotion ..> Sale : calculates discount