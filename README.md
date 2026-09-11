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

