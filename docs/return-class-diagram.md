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
    %% SALE
    %% =========================

    class Sale {
        -LocalDate date
        -List~Product~ products
        +void addProduct(Product product)
        +List~Product~ getProducts()
        +double calculateTotal()
        +boolean canBeReturned()
    }

    %% =========================
    %% RETURN
    %% =========================

    class Return {
        -LocalDate returnDate
        -Sale originalSale
        -List~Product~ returnedProducts
        -String reason
        -double refundAmount
        +Return(LocalDate returnDate, Sale originalSale, List~Product~ returnedProducts, String reason)
        +double calculateRefundAmount()
        +String generateReturnReceipt()
    }

    %% =========================
    %% PERSISTENCE
    %% =========================

    class ReturnRepository {
        -String FILE_PATH
        +ReturnRepository()
        +List~Return~ loadAll()
        +void saveAll(List~Return~ returns)
    }

    %% =========================
    %% SERVICE
    %% =========================

    class ReturnService {
        -ReturnRepository returnRepository
        +ReturnService(ReturnRepository returnRepository)
        +void registerReturn(Return returnItem)
        +List~Return~ listAllReturns()
        +double calculateMonthlyReturnBalance(int month, int year)
    }

    %% =========================
    %% PRODUCT SERVICE
    %% =========================

    class ProductService {
        +void restoreStock(Product product, int quantity)
    }

    %% =========================
    %% MENU
    %% =========================

    class ConsoleMenu {
        +void showReturnMenu()
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================

    Sale "1" --> "1..*" Product : contains

    Return "1" --> "1" Sale : original sale
    Return "1" --> "1..*" Product : returned products

    ReturnRepository --> Return : persists

    ReturnService --> ReturnRepository : uses
    ReturnService --> Return : manages

    ReturnService --> Sale : validates return
    ReturnService --> ProductService : restores stock

    ConsoleMenu --> ReturnService : manages returns