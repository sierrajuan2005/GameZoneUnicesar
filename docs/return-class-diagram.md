# Diagrama de Clases - Devoluciones

```mermaid
classDiagram

    class Return {
        -String identifier
        -LocalDate returnDate
        -Sale originalSale
        -List~Product~ returnedProducts
        -String reason
        -double refundAmount
        +Return(String identifier, LocalDate returnDate, Sale originalSale, List~Product~ returnedProducts, String reason)
        +String getIdentifier()
        +LocalDate getReturnDate()
        +Sale getOriginalSale()
        +List~Product~ getReturnedProducts()
        +String getReason()
        +double getRefundAmount()
        +double calculateRefundAmount()
        +String generateReturnReceipt()
    }

    class ReturnRepository {
        -String FILE_PATH
        -SaleService saleService
        -ProductService productService
        +ReturnRepository(SaleService saleService, ProductService productService)
        +void saveAll(List~Return~ returns)
        +List~Return~ loadAll()
        -String convertToCsv(Return r)
        -Return convertFromCsv(String line)
    }

    class ReturnService {
        -ReturnRepository returnRepository
        -SaleService saleService
        -ProductService productService
        +ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService)
        +Return registerReturn(String identifier, List~String~ productIds, String reason)
        +List~Return~ viewAllReturns()
        +List~Return~ viewReturnsByCustomer(String customerId)
        +List~Return~ viewReturnsBySale(String saleId)
        +double generateMonthlyBalance(int month, int year)
    }

    class SaleService {
        -SaleRepository saleRepository
        -ProductService productService
        -AccessoryService accessoryService
        -WarrantyService warrantyService
        -PromotionService promotionService
        +Sale findSaleById(String saleId)
        +List~Sale~ listSales()
    }

    class ProductService {
        -ProductRepository productRepository
        +Product findByIdentifier(String identifier)
        +void updateStock(Product product, int quantity)
        +void restoreStock(String productId, int quantity)
        +List~Product~ listProducts()
    }

    Return --> Sale : originalSale
    Return --> Product : returnedProducts

    ReturnRepository --> Return : persists
    ReturnRepository --> SaleService : uses
    ReturnRepository --> ProductService : uses

    ReturnService --> ReturnRepository : uses
    ReturnService --> SaleService : uses
    ReturnService --> ProductService : uses
    ReturnService --> Return : creates

    ReturnService ..> Sale : validates
    ReturnService ..> Product : restores stock