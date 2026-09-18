# GameZone Unicesar

Information system for managing a video game and console store located in the university district of Valledupar. The system supports the management of products, accessories, customers, sellers, sales, returns, and promotions, with data persistence between executions.

Project developed as part of the Object-Oriented Programming workshop, applying a layered architecture consisting of **model, persistence, service, and user interface** layers.

---

# Features

## Product Management

* Register video games with platform, genre, and age rating.
* Register consoles with brand, model, and generation.
* List the available product inventory.

## Person Management

* Register customers with name, identification, phone, and email.
* List registered customers.
* List registered sellers.
* Sellers are preloaded the first time the application starts and are not registered through the user interface.

## Sale Management

* Register a sale with a customer, a seller, and one or more products.
* Validate that a sale contains at least one product.
* Validate product stock before completing a sale.
* Automatically update inventory when a sale is registered.
* List all registered sales.
* Query the purchase history of a specific customer.
* Query the sales handled by a specific seller.

## Return Management

* Record the return of one or more products from an existing sale.
* Support partial returns.
* Validate that returns are made within 30 days of the original sale.
* Validate that returned products belong to the specified sale.
* Automatically restore inventory when a return is successfully processed.
* View all registered returns.
* View returns by customer.
* View returns associated with a specific sale.
* Generate a monthly balance report based on sales minus returns for a specified month and year.

## Promotion Management

* Register promotions of three types:

    * Percentage-based promotions.
    * Category-based promotions for video games or consoles.
    * Bulk purchase promotions.
* Validate promotion validity based on a start and end date.
* Automatically apply the best available promotion when registering a sale.
* Promotions are not cumulative; only the promotion with the highest monetary discount is applied.
* Display the promotion name and applied discount on the sales receipt.
* View all registered promotions.
* View only currently active promotions.

---

# Accessory Management

The accessory module extends the existing product hierarchy and integrates with the current sales and inventory system.

## Implemented Accessory Types

The system supports three types of accessories:

* **Controller** – includes the connection type.
* **Cable** – includes the cable length and connector type.
* **Memory** – includes the storage capacity and memory type.

All accessory types extend the `Accessory` class, which in turn extends `Product`.

This allows accessories to reuse common product attributes and behavior such as:

* Identifier.
* Title.
* Price.
* Available stock.

The hierarchy is:

```text
Product
   ↓
Accessory
   ├── Controller
   ├── Cable
   └── Memory
```

## Accessory Operations

The `AccessoryService` provides the following operations:

* Register controllers.
* Register cables.
* Register memory devices.
* List all registered accessories.
* Filter accessories by type.
* Find accessories compatible with a specific console.
* Search accessories by identifier.
* Update accessory stock.

## Console Compatibility

Accessories can be associated with the consoles they are compatible with.

The compatibility relationship is represented through a list of `Console` objects inside the `Accessory` class.

The system allows users to select a console and find the accessories compatible with it.

## Accessory Persistence

Accessory information is persisted through the `AccessoryRepository` using:

```text
data/accessories.csv
```

The repository identifies each accessory by its type and reconstructs the corresponding subclass when the data is loaded.

## Accessory Integration

The accessory module is integrated into the existing application through dependency injection.

The main application connects:

```text
Main
 ├── AccessoryRepository
 │        ↓
 │   AccessoryService
 │        ↓
 ├── SaleService
 │
 └── ConsoleUI
          ↓
    AccessoryService
```

This allows accessories to participate in the existing product, inventory, sales, and user interface flow without replacing the previous product functionality.

---

# Persistence

The application uses text-based files to preserve information between executions.

The main data files are stored inside the `data/` directory:

```text
data/
├── products.csv
├── people.csv
├── sales.csv
├── returns.csv
├── promotions.csv
└── accessories.csv
```

Repositories are responsible for reading and writing persistent data, while services contain the corresponding business rules.

---

# Architecture

The project follows a layered architecture with the following dependency direction:

```text
User Interface
       ↓
   Services
       ↓
  Persistence
       ↓
     Model
```

The main project structure is:

```text
src/main/java/com/gamezone/

├── model/
│   ├── Person
│   ├── Customer
│   ├── Seller
│   ├── Product
│   ├── VideoGame
│   ├── Console
│   ├── Accessory
│   ├── Controller
│   ├── Cable
│   ├── Memory
│   ├── Sale
│   ├── Return
│   └── Promotion
│
├── persistence/
│   ├── PersonRepository
│   ├── ProductRepository
│   ├── SaleRepository
│   ├── ReturnRepository
│   ├── PromotionRepository
│   └── AccessoryRepository
│
├── services/
│   ├── PersonService
│   ├── ProductService
│   ├── SaleService
│   ├── ReturnService
│   ├── PromotionService
│   ├── WarrantyService
│   └── AccessoryService
│
├── ui/
│   └── ConsoleUI
│
└── Main.java
```

The **model layer** represents the domain entities.

The **service layer** contains business rules and application logic.

The **persistence layer** handles file-based data storage.

The **UI layer** is responsible for interaction with the user through the console.

This structure promotes code reuse, separation of responsibilities, maintainability, and clear dependency management.

---

# Requirements

* Java 17 or higher.
* Maven 3.8 or higher.

---

# Build

From the project root:

```bash
mvn clean compile
```

To package the project into a `.jar`:

```bash
mvn clean package
```

---

# Run

Run the application from the project root using the configured Maven command or IDE.

The first time the application runs, the system automatically preloads three default sellers and creates the `data/` directory where persistent information is stored.

---

# Git Workflow

The team follows a simplified Git Flow model:

```text
main
  ↓
Stable version of the system.
Protected against direct writes.

develop
  ↓
Team integration branch.
Protected against direct writes.

feature/*
  ↓
Feature branches.
Merged into develop through Pull Requests.
```

Each Pull Request is reviewed by another team member before being merged.

Every commit follows the **Conventional Commits** convention and is written in English:

```text
feat:
fix:
refactor:
docs:
chore:
```

---

# Team

See [`TEAM.md`](TEAM.md) for the complete team information, assigned roles, and class distribution.
