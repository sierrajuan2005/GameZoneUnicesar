# Diagrama General de Clases - GameZoneUnicesar

```mermaid
classDiagram

%% =========================================================
%% ====================== MODEL =============================
%% =========================================================

class Person {
    <<abstract>>
    -String name
    -String identification
    -String phone
    +getName()
    +getIdentification()
    +getPhone()
}

class Customer {
    -String email
    +Customer(String name, String identification, String phone, String email)
    +getEmail()
}

class Seller {
    -String employeeCode
    -String workShift
    +Seller(String name, String identification, String phone, String employeeCode, String workShift)
    +getEmployeeCode()
    +getWorkShift()
}

Person <|-- Customer
Person <|-- Seller


class Product {
    <<abstract>>
    -String identifier
    -String title
    -double price
    -int availableQuantity
    +getIdentifier()
    +getTitle()
    +getPrice()
    +getAvailableQuantity()
    +setAvailableQuantity(int)
    +getDescription()*
}

class VideoGame {
    -String platform
    -String genre
    -String ageRating
}

class Console {
    -String brand
    -String model
    -String generation
    +Console(String identifier, String title, double price, int availableQuantity, String brand, String model, String generation)
    +getBrand()
    +setBrand(String)
    +getModel()
    +setModel(String)
    +getGeneration()
    +setGeneration(String)
    +getDescription()
}

Product <|-- VideoGame
Product <|-- Console


class Accessory {
    <<abstract>>
    -List~Console~ compatibleConsoles
}

class Controller {
    -String connectionType
    +Controller(...)
    +getConnectionType()
    +setConnectionType(String)
    +getDescription()
}

class Cable {
    -double length
    -String connectorType
    +Cable(...)
    +getLength()
    +setLength(double)
    +getConnectorType()
    +setConnectorType(String)
    +getDescription()
}

class Memory {
    -int capacity
    -String memoryType
    +Memory(...)
    +getCapacity()
    +setCapacity(int)
    +getMemoryType()
    +setMemoryType(String)
    +getDescription()
}

Product <|-- Accessory
Accessory <|-- Controller
Accessory <|-- Cable
Accessory <|-- Memory

Accessory --> "0..*" Console : compatible with


class Sale {
    -String identifier
    -LocalDate date
    -Customer customer
    -Seller seller
    -List~Product~ products
    +calculateTotal()
    +generateReceipt()
}

Sale --> "1" Customer : customer
Sale --> "1" Seller : seller
Sale --> "1..*" Product : products


class Warranty {
    <<abstract>>
    -String identifier
    -Product product
    -Sale sale
    -LocalDate startDate
    -LocalDate endDate
    +getIdentifier()
    +getProduct()
    +getSale()
    +getStartDate()
    +getEndDate()
    +getDurationInMonths()*
    +generateWarrantyCertificate()
}

class BasicWarranty {
}

class ExtendedWarranty {
}

Warranty <|-- BasicWarranty
Warranty <|-- ExtendedWarranty

Warranty --> "1" Product : product
Warranty --> "1" Sale : sale


class Return {
    -Sale sale
    -List~Product~ products
    -String reason
    +generateReturnReceipt()
}

Return --> "1" Sale : sale
Return --> "1..*" Product : returned products


class Promotion {
    <<abstract>>
    -String identifier
    -String name
    -LocalDate startDate
    -LocalDate endDate
}

class PercentageDiscount {
    -double discountPercentage
}

class CategoryDiscount {
    -double discountPercentage
    -String targetCategory
}

class BulkPurchaseDiscount {
    -int minimumQuantity
    -double discountPercentage
}

Promotion <|-- PercentageDiscount
Promotion <|-- CategoryDiscount
Promotion <|-- BulkPurchaseDiscount


%% =========================================================
%% =================== PERSISTENCE =========================
%% =========================================================

class PersonRepository {
    -String FILE_PATH
    +saveAll(List~Person~)
    +loadAll()
    +addPerson(Person)
    +getPeople()
    +findByIdentification(String)
    +removePerson(String)
}

class ProductRepository {
}

class AccessoryRepository {
    -String FILE_PATH
    +saveAll(List~Accessory~)
    +loadAll()
}

class SaleRepository {
}

class WarrantyRepository {
}

class ReturnRepository {
}

class PromotionRepository {
    -String FILE_PATH
    +saveAll(List~Promotion~)
    +loadAll()
}

PersonRepository ..> Person : persists
PersonRepository ..> Customer : creates
PersonRepository ..> Seller : creates

AccessoryRepository ..> Accessory : persists
AccessoryRepository ..> Controller : creates
AccessoryRepository ..> Cable : creates
AccessoryRepository ..> Memory : creates

PromotionRepository ..> Promotion : persists
PromotionRepository ..> PercentageDiscount : creates
PromotionRepository ..> CategoryDiscount : creates
PromotionRepository ..> BulkPurchaseDiscount : creates

ProductRepository ..> Product : persists
SaleRepository ..> Sale : persists
WarrantyRepository ..> Warranty : persists
ReturnRepository ..> Return : persists


%% =========================================================
%% ====================== SERVICES ==========================
%% =========================================================

class PersonService {
    -PersonRepository personRepository
    +PersonService(PersonRepository)
    +PersonService()
    +addPerson(Person)
    +getAllPeople()
    +findPersonByIdentification(String)
    +removePerson(String)
    +preloadSellers(PersonService)
}

class ProductService {
    -ProductRepository productRepository
}

class AccessoryService {
    -AccessoryRepository accessoryRepository
    +AccessoryService(AccessoryRepository)
    +registerController(...)
    +registerCable(...)
    +registerMemory(...)
    +listAllAccessories()
    +listAccessoriesByType(String)
    +findAccessoriesCompatibleWith(String)
    +findById(String)
    +updateStock(String, int)
}

class SaleService {
    -SaleRepository saleRepository
    -ProductService productService
    -AccessoryService accessoryService
    -WarrantyService warrantyService
    -PromotionService promotionService
}

class WarrantyService {
    -WarrantyRepository warrantyRepository
}

class ReturnService {
    -ReturnRepository returnRepository
    -SaleService saleService
    -ProductService productService
}

class PromotionService {
    -PromotionRepository promotionRepository
}

PersonService --> PersonRepository
ProductService --> ProductRepository
AccessoryService --> AccessoryRepository
SaleService --> SaleRepository
SaleService --> ProductService
SaleService --> AccessoryService
SaleService --> WarrantyService
SaleService --> PromotionService
WarrantyService --> WarrantyRepository
ReturnService --> ReturnRepository
ReturnService --> SaleService
ReturnService --> ProductService
PromotionService --> PromotionRepository


%% =========================================================
%% ======================== UI ==============================
%% =========================================================

class ConsoleUI {
    -PersonService personService
    -ProductService productService
    -SaleService saleService
    -WarrantyService warrantyService
    -ReturnService returnService
    -PromotionService promotionService
    -AccessoryService accessoryService
    -Scanner scanner

    +ConsoleUI(...)
    +start()
    +showMainMenu()
    +showProductMenu()
    +showPersonMenu()
    +showSaleMenu()
    +showWarrantyMenu()
    +showReturnMenu()
    +showPromotionMenu()
}

ConsoleUI --> PersonService
ConsoleUI --> ProductService
ConsoleUI --> AccessoryService
ConsoleUI --> SaleService
ConsoleUI --> WarrantyService
ConsoleUI --> ReturnService
ConsoleUI --> PromotionService


%% =========================================================
%% ======================== MAIN =============================
%% =========================================================

class Main {
    +main(String[])
    -preloadSellers(PersonService)
}

Main ..> PersonRepository
Main ..> ProductRepository
Main ..> AccessoryRepository
Main ..> SaleRepository
Main ..> PromotionRepository
Main ..> WarrantyRepository
Main ..> ReturnRepository

Main ..> PersonService
Main ..> ProductService
Main ..> AccessoryService
Main ..> SaleService
Main ..> PromotionService
Main ..> WarrantyService
Main ..> ReturnService
Main ..> ConsoleUI