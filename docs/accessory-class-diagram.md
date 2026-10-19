# Diagrama de Clases

```mermaid
classDiagram

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

    class Console {
        -String brand
        -String model
        -String generation
        +Console(String identifier, String title, double price, int availableQuantity, String brand, String model, String generation)
        +String getBrand()
        +void setBrand(String brand)
        +String getModel()
        +void setModel(String model)
        +String getGeneration()
        +void setGeneration(String generation)
        +String getDescription()
    }

    class AccessoryRepository {
        -String FILE_PATH
        +AccessoryRepository()
        +void saveAll(List~Accessory~ accessories)
        +List~Accessory~ loadAll()
    }

    class AccessoryService {
        -AccessoryRepository accessoryRepository
        +AccessoryService(AccessoryRepository accessoryRepository)
        +void registerController(String id, String title, double price, int availability, List~Console~ consoles, String connectionType)
        +void registerCable(String id, String title, double price, int availability, List~Console~ consoles, double length, String connectorType)
        +void registerMemory(String id, String title, double price, int availability, List~Console~ consoles, int capacity, String memoryType)
        +List~Accessory~ listAllAccessories()
        +List~Accessory~ listAccessoriesByType(String type)
        +List~Accessory~ findAccessoriesCompatibleWith(String consoleId)
        +Accessory findById(String id)
        +void updateStock(String accessoryId, int quantity)
    }

    Product <|-- Accessory
    Product <|-- Console

    Accessory <|-- Controller
    Accessory <|-- Memory
    Accessory <|-- Cable

    Accessory "0..*" --> "0..*" Console : compatible with

    AccessoryService --> AccessoryRepository : uses
    AccessoryRepository --> Accessory : persists