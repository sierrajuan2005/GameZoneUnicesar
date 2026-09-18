---
config:
layout: dagre
flowchart TB
subgraph UI["User Interface Layer"]
direction TB
ConsoleUI["ConsoleUI"]
end
subgraph Services["Services Layer"]
direction TB
PersonService["PersonService"]
ProductService["ProductService"]
SaleService["SaleService"]
end
subgraph Persistence["Persistence Layer"]
direction TB
PersonRepository["PersonRepository"]
ProductRepository["ProductRepository"]
SaleRepository["SaleRepository"]
end
subgraph Model["Model Layer"]
direction TB
Person["Person"]
Customer["Customer"]
Seller["Seller"]
Product["Product"]
VideoGame["VideoGame"]
Console["Console"]
Sale["Sale"]
end
Main["Main"] --> ConsoleUI
ConsoleUI --> PersonService & ProductService & SaleService
PersonService --> PersonRepository
ProductService --> ProductRepository
SaleService --> SaleRepository
PersonRepository --> Person
ProductRepository --> Product
SaleRepository --> Sale
PersonService -.-> Customer & Seller
ProductService -.-> Product
SaleService -.-> Customer & Seller & Product
Product --> VideoGame & Console
ConsoleUI:::ui
PersonService:::service
ProductService:::service
SaleService:::service
PersonRepository:::persistence
ProductRepository:::persistence
SaleRepository:::persistence
Person:::model
Customer:::model
Seller:::model
Product:::model
VideoGame:::model
Console:::model
Sale:::model
Main:::entry
classDef entry fill:#fefce8,stroke:#facc15,stroke-width:2px
classDef ui fill:#eef2ff,stroke:#818cf8,stroke-width:2px
classDef service fill:#f0fdfa,stroke:#2dd4bf,stroke-width:2px
classDef persistence fill:#fff7ed,stroke:#fb923c,stroke-width:2px
classDef model fill:#f5f3ff,stroke:#a78bfa,stroke-width:2px