# Diagrama de Clases - Garantías

```mermaid
classDiagram

    class Warranty {
        <<abstract>>
        -String identifier
        -Product product
        -Sale sale
        -LocalDate startDate
        -LocalDate endDate
        +Warranty(String identifier, Product product, Sale sale, LocalDate startDate)
        +String getIdentifier()
        +Product getProduct()
        +Sale getSale()
        +LocalDate getStartDate()
        +LocalDate getEndDate()
        +int getDurationInMonths()*
        +String getWarrantyType()*
        +double getAdditionalCost()*
        +boolean isActive(LocalDate currentDate)
        +String generateWarrantyCertificate()
    }

    class BasicWarranty {
        +BasicWarranty(String identifier, Product product, Sale sale, LocalDate startDate)
        +int getDurationInMonths()
        +String getWarrantyType()
        +double getAdditionalCost()
    }

    class ExtendedWarranty {
        +ExtendedWarranty(String identifier, Product product, Sale sale, LocalDate startDate)
        +int getDurationInMonths()
        +String getWarrantyType()
        +double getAdditionalCost()
    }

    class WarrantyRepository {
        -String FILE_PATH
        -List~Warranty~ warranties
        -SaleRepository saleRepository
        -ProductRepository productRepository
        +WarrantyRepository(SaleRepository saleRepository, ProductRepository productRepository)
        +void addWarranty(Warranty warranty)
        +List~Warranty~ getAllWarranties()
        +Warranty findByIdentifier(String identifier)
        +List~Warranty~ loadAll()
        -String convertToCsv(Warranty warranty)
        -Warranty convertFromCsv(String line)
        -void saveAll(List~Warranty~ warranties)
    }

    class WarrantyService {
        -WarrantyRepository warrantyRepository
        +WarrantyService(WarrantyRepository warrantyRepository)
        +void registerWarranty(Warranty warranty)
        +List~Warranty~ listWarranties()
        +Warranty findWarrantyByIdentifier(String identifier)
        +List~Warranty~ reloadWarranties()
        +BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate)
        +ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate)
        +Warranty findWarrantyByProduct(String productIdentifier, String saleIdentifier)
        +List~Warranty~ listActiveWarranties()
        +List~Warranty~ listWarrantiesExpiringSoon(int daysAhead)
    }

    class Product {
        <<abstract>>
    }

    class Sale {
    }

    class SaleRepository {
    }

    class ProductRepository {
    }

    Warranty <|-- BasicWarranty
    Warranty <|-- ExtendedWarranty

    Warranty --> Product : product
    Warranty --> Sale : sale

    WarrantyRepository --> Warranty : manages
    WarrantyRepository --> SaleRepository : uses
    WarrantyRepository --> ProductRepository : uses

    WarrantyService --> WarrantyRepository : uses
    WarrantyService --> Warranty : manages