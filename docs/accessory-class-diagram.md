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
        +Product(String identifier, String title, double price, int availableQuantity)
        +String getIdentifier()
        +String getTitle()
        +double getPrice()
        +int getAvailableQuantity()
        +void setIdentifier(String identifier)
        +void setTitle(String title)
        +void setPrice(double price)
        +void setAvailableQuantity(int availableQuantity)
        +String getDescription()
    }

    %% =========================
    %% ACCESSORY HIERARCHY
    %% =========================

    class Accessory {
        <<abstract>>
        -List~Console~ compatibleConsoles
        +Accessory(String identifier, String title, double price, int availableQuantity, List~Console~ compatibleConsoles)
        +List~Console~ getCompatibleConsoles()
        +void setCompatibleConsoles(List~Console~ compatibleConsoles)
        +String getDescription()
    }

    class Controller {
        -String connectionType
        +Controller(String identifier, String title, double price, int availableQuantity, List~Console~ compatibleConsoles, String connectionType)
        +String getConnectionType()
        +void setConnectionType(String connectionType)
        +String getDescription()
    }

    class Cable {
        -double length
        -String connectorType
        +Cable(String identifier, String title, double price, int availableQuantity, List~Console~ compatibleConsoles, double length, String connectorType)
        +double getLength()
        +void setLength(double length)
        +String getConnectorType()
        +void setConnectorType(String connectorType)
        +String getDescription()
    }

    class Memory {
        -int capacity
        -String memoryType
        +Memory(String identifier, String title, double price, int availableQuantity, List~Console~ compatibleConsoles, int capacity, String memoryType)
        +int getCapacity()
        +void setCapacity(int capacity)
        +String getMemoryType()
        +void setMemoryType(String memoryType)
        +String getDescription()
    }

    %% =========================
    %% CONSOLE
    %% =========================

    class Console {
        -String identifier
        -String title
        -double price
        -int availableQuantity
    }

    %% =========================
    %% PERSISTENCE
    %% =========================

    class AccessoryRepository {
        -String FILE_PATH
        +AccessoryRepository()
        +List~Accessory~ loadAll()
        +void saveAll(List~Accessory~ accessories)
        +Optional~Accessory~ findById(String identifier)
    }

    %% =========================
    %% SERVICE
    %% =========================

    class AccessoryService {
        -AccessoryRepository accessoryRepository
        +AccessoryService(AccessoryRepository accessoryRepository)
        +void registerController(Controller controller)
        +void registerCable(Cable cable)
        +void registerMemory(Memory memory)
        +List~Accessory~ listAllAccessories()
        +List~Accessory~ listAccessoriesByType(String type)
        +List~Accessory~ findAccessoriesCompatibleWith(Console console)
        +void updateStock(String identifier, int quantity)
    }

    %% =========================
    %% SALE
    %% =========================

    class Sale {
        -LocalDate date
        -List~Product~ products
        +void addProduct(Product product)
        +double calculateTotal()
        +boolean canBeReturned()
    }

    class SaleService {
        +void registerSale(Sale sale)
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================

    Product <|-- Accessory

    Accessory <|-- Controller
    Accessory <|-- Cable
    Accessory <|-- Memory

    Accessory "0..*" --> "0..*" Console : compatible with

    AccessoryRepository --> Accessory : stores

    AccessoryService --> AccessoryRepository : uses
    AccessoryService --> Accessory : manages

    Sale "1" --> "1..*" Product : contains

    SaleService --> Sale : manages