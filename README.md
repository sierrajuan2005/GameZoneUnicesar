# GameZone Unicesar

Information system for managing a video game and console store located in the university district of Valledupar. The system supports the management of products, accessories, customers, sellers, sales, returns, promotions, and warranties, with data persistence between executions.

Project developed as part of the Object-Oriented Programming workshop, applying a layered architecture consisting of **model, persistence, service, and user interface** layers, complemented by a cross-cutting **exceptions** layer and a **validation** layer.

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

## Warranty Management

* Warranties are generated automatically for consoles when a sale is registered.
* An extended warranty can be added to a console at the moment of the sale, with an additional cost of 10%.
* Check the warranty of a product within a specific sale.
* List all registered warranties.
* List active warranties.
* List warranties that are about to expire within a given number of days.
* Warranties associated with a console are voided when that console is returned.

## Integrated System

* A sale can combine products and accessories; the system automatically applies the best available promotion, generates warranties for consoles, and calculates a final total that includes discounts and extended warranties.
* Returns replenish the corresponding inventory (product or accessory), calculate a refund proportional to the original discount, and void any associated warranty if a console is returned.
* The monthly balance sheet breaks down total sales, total returns, and the net balance.

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

Accessories can be sold together with video games and consoles in a single transaction.

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
├── sales.txt
├── returns.csv
├── promotions.csv
└── accessories.csv
```

Repositories are responsible for reading and writing persistent data, while services contain the corresponding business rules. Repositories wrap any I/O error in a `PersistenceException`.

---

# Architecture

The project follows a layered architecture with the following dependency direction:

```text
User Interface
       ↓
   Services  ──→  Validation
       ↓
  Persistence
       ↓
     Model

Exceptions: cross-cutting layer, usable from any layer.
```

The main project structure is:

```text
src/main/java/com/gamezone/

├── exceptions/
│   ├── GameZoneException
│   ├── ResourceNotFoundException
│   ├── BusinessRuleException
│   ├── InvalidDataException
│   └── PersistenceException
│
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
│   ├── Promotion (+ subclasses)
│   └── Warranty (+ subclasses)
│
├── persistence/
│   ├── PersonRepository
│   ├── ProductRepository
│   ├── SaleRepository
│   ├── ReturnRepository
│   ├── PromotionRepository
│   ├── AccessoryRepository
│   ├── WarrantyRepository
│   └── WarrantyRecord
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
├── validation/
│   ├── ProductValidator
│   ├── PersonValidator
│   └── SaleValidator
│
└── Main.java
```

The **model layer** represents the domain entities.

The **service layer** contains business rules and application logic.

The **persistence layer** handles file-based data storage.

The **UI layer** is responsible for interaction with the user through the console.

The **validation layer** concentrates the verification of data and business rules.

The **exceptions layer** defines the custom exception hierarchy used across the system.

This structure promotes code reuse, separation of responsibilities, maintainability, and clear dependency management.

---

# Exception Handling and Validation Layer

This layer was added in Requirement 6. Its goal is to separate validation
logic from business logic and to tell the user clearly what went wrong,
instead of showing the same generic error for every failure.

## Packages

| Package | Responsibility |
|---|---|
| `com.gamezone.exceptions` | Custom exception hierarchy. It is a cross-cutting layer: any other layer can use it. |
| `com.gamezone.validation` | Validators that check data and business rules and throw the custom exceptions. |

## Exception hierarchy

`GameZoneException` is the root class. It extends `RuntimeException` and stores
an error code. Four specialized subclasses extend it:

| Exception | Thrown when | Example |
|---|---|---|
| `ResourceNotFoundException` | A requested resource does not exist | Searching a sale or customer with an unknown identifier |
| `BusinessRuleException` | A business rule is violated | Insufficient stock, duplicated person, sale without products |
| `InvalidDataException` | An input value has an invalid format or range | Negative price, empty title, malformed email |
| `PersistenceException` | Reading or writing a file fails | Missing or unreadable data file |

`PersistenceException` always keeps the original cause, so no traceability is lost.

## Validators

Each functional module has a validator with static methods:

- `ProductValidator`: product data, stock availability, product existence.
- `PersonValidator`: person data, person existence, unique identifier.
- `SaleValidator`: sale data (customer, seller, products), sale existence.

Services call the validators before running any business operation. Validators
never call services or repositories and contain no file access logic.

## How the layers work together

```text
ConsoleUI  ->  Service  ->  Validator  (throws GameZoneException subclasses)
                  |
                  +------>  Repository (wraps IOException in PersistenceException)
```

- **Services** delegate validation to the validators and throw typed exceptions
  instead of `IllegalArgumentException`.
- **Repositories** catch `IOException` internally and rethrow it as
  `PersistenceException`, including the file name and the operation
  (read or write) in the message.
- **ConsoleUI** catches each exception type separately and shows a different
  message to the user. The user never sees a technical message or a stack trace.

Dependency direction: `ui -> service -> persistence -> model`. The `validation`
layer sits between `service` and `model`, and `exceptions` can be used from any layer.

## Messages shown to the user

| Exception | Message (in Spanish) |
|---|---|
| `ResourceNotFoundException` | `No se encontró el recurso solicitado: <detalle>` |
| `BusinessRuleException` | `No se puede completar la operación: <detalle>` |
| `InvalidDataException` | `Los datos ingresados no son válidos: <detalle>` |
| `PersistenceException` | `Ocurrió un problema al acceder a los datos del sistema. Contacte al administrador.` |

## Behavior preserved

This change does not add or modify any menu operation. The existing operations
work the same way for valid input. Only the quality of the error messages
changes.

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