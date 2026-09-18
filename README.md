GameZone Unicesar



Information system for managing a video game and console store located in the university district of Valledupar. It supports managing products, customers, sellers and sales, with data persistence between executions.



Project developed as part of the Object-Oriented Programming workshop, applying a layered architecture (model, persistence, service, user interface).



Features



Product management



Register video games (platform, genre, age rating).

Register consoles (brand, model, generation).

List the available product inventory.



Person management



Register customers (name, identification, phone, email).

List registered customers.

List registered sellers (sellers are preloaded the first time the application starts; they are not registered through the user interface).



Sale management



Register a sale with a customer, a seller and one or more products.

Business rule validation: a sale requires at least one product, and a product cannot be sold if there is not enough stock. The inventory is automatically discounted when a sale is registered.

List all registered sales.

Query the purchase history of a specific customer.

Query the sales handled by a specific seller.



Persistence



All information (products, people, sales) is stored in text files inside the data/ folder, and is preserved between executions of the application.

Architecture



The project is organized into four layers, following the dependency direction user interface → service → persistence → model:



src/main/java/com/gamezone/

├── model/         # Domain classes: Person, Customer, Seller, Product, VideoGame, Console, Sale

├── persistence/    # File access: PersonRepository, ProductRepository, SaleRepository

├── services/       # Business rules: PersonService, ProductService, SaleService

├── ui/             # Console interface: ConsoleUI

└── Main.java       # Application entry point

Team



See TEAM.md for the complete team information, assigned roles and class distribution.



Requirements

Java 17 or higher

Maven 3.8 or higher

Build



From the project root:



bash

mvn clean compile



To package the project into a .jar:



bash

mvn clean package

Run



The first time the application runs, the system automatically preloads 3 default sellers and creates the data/ folder where the information is stored.



Git workflow



The team works under the simplified Git Flow model:



main: stable version of the system, protected against direct writes.

develop: the team's integration branch, protected against direct writes.

feature/\*: feature branches, merged into develop through a Pull Request reviewed by another team member.



Every commit follows the Conventional Commits convention (feat:, fix:, refactor:, docs:, chore:), written in English.





Returns Management



Record the return of one or more products from an existing sale (the return may be partial).

Timeframe validation: Returns can only be recorded within 30 days of the original sale.

Belonging validation: Returned products must actually belong to the specified sale.

Automatic inventory update upon processing a successful return.

View all returns, by customer, or by specific sale.

Generate a monthly balance report (sales minus returns) for a specified month and year.



Promotion Management



Register promotions of three types: percentage-based, category-based (video games or consoles), and bulk purchase-based.

Validity by date range: a promotion is only applied if the current date falls within its validity period.

Automatic application of the best available promotion when registering a sale (promotions are not cumulative; only the one with the highest monetary discount is applied).

The sales receipt displays the subtotal, the applied discount (including the promotion name), and the final total.

View all registered promotions or only those currently active.

## Accessory Module Implementation

The accessory module was integrated into the existing GameZone architecture while maintaining the current product hierarchy and layered design.

### Implemented Features

The module supports three types of accessories:

* **Controller** – includes the connection type.
* **Cable** – includes the cable length and connector type.
* **Memory** – includes the storage capacity and memory type.

All accessory types extend the `Accessory` class, which in turn extends `Product`. This allows accessories to reuse the common product attributes and behavior, such as identifier, title, price, and available stock.

### Accessory Management

The `AccessoryService` provides the following operations:

* Register controllers.
* Register cables.
* Register memory devices.
* List all registered accessories.
* Filter accessories by type.
* Find accessories compatible with a specific console.
* Search accessories by identifier.
* Update accessory stock.

### Console Compatibility

Accessories can be associated with the consoles they are compatible with. The compatibility relationship is represented through a list of `Console` objects inside the `Accessory` class.

The system allows users to search for all accessories compatible with a selected console.

### Persistence

Accessory information is persisted through the `AccessoryRepository` using the file:

`data/accessories.csv`

The repository identifies each accessory by its type and reconstructs the corresponding subclass when the data is loaded.

### Main Application Integration

The accessory module was integrated into the main application through dependency injection.

`Main` now creates and connects:

* `AccessoryRepository`
* `AccessoryService`
* `AccessoryService` in `SaleService`
* `AccessoryService` in `ConsoleUI`

The resulting dependency flow is:

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

This integration allows the accessory module to work together with the existing product, sales, and user interface components without breaking the previous functionality.

### Architectural Design

The implementation follows the layered architecture used by the project:

```text
UI Layer
    ↓
Service Layer
    ↓
Persistence Layer
    ↓
Data Storage
```

The model classes represent the domain entities, services contain business logic, repositories handle persistence, and `ConsoleUI` is responsible for interaction with the user.

This structure promotes code reuse, separation of responsibilities, and maintainability.


