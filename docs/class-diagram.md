---
config:
  layout: dagre
  class:
    hideEmptyMembersBox: true
---
classDiagram
direction TB
    class Person {
	    -name: String
	    -identification: String
	    -phone: String
	    +getName() :String
	    +setName(name: String) : void
	    +getIdentification() : String
	    +setIdentification(identification: String) : void
	    +getPhone() : String
	    +setPhone(phone: String) : void
    }

    class Customer {
	    -email: String
	    -purchaseHistory: List
	    +getEmail() : String
	    +setEmail(email: String) :void
	    +getPurchaseHistory() : List
	    +addToPurchaseHistory(sale: Sale) : void
    }

    class Seller {
	    -employeeCode: String
	    -workShift: String
	    +getEmployeeCode() : String
	    +setEmployeeCode(employeeCode: String) :void
	    +getWorkShift() : String
	    +setWorkShift(workShift: String) :void
    }

    class Product {
	    -identifier: String
	    -title: String
	    -price: double
	    -availableQuantity: int
	    +getIdentifier() : String
	    +setIdentifier(identifier: String) :void
	    +getTitle() : String
	    +setTitle(title: String) :void
	    +getPrice(): double
	    +setPrice(price: double) : void
	    +getAvailableQuantity(): int
	    +setAvailableQuantity(availableQuantity: int): void
	    +getDescription() : String
    }

    class Console {
	    -brand: String
	    -model: String
	    -generation: String
	    +getBrand() : String
	    +setBrand(brand: String) :void
	    +getModel(): String
      +setModel(model: String): void
	    +getGeneration() : String
	    +setGeneration(generation: String) :void
	    +getDescription() : String
    }

    class VideoGame {
	    -platform: String
	    -genre: String
	    -ageRating: String
	    +getPlatform() : String
	    +setPlatform(platform: String) :void
	    +getGenre() : String
	    +setGenre(genre: String) :void
	    +getAgeRating(): String
      +setAgeRating(ageRating: String): void
	    +getDescription() : String
    }

    class Sale {
	    -date: LocalDate
	    -customer: Customer
	    -seller: Seller
	    -products: List
	    +getDate() : LocalDate
	    +setDate(date: LocalDate) :void
	    +getCustomer() : Customer
	    ++setCustomer(customer: Customer): void
	    +getSeller() : Seller
	    +setSeller(seller: Seller) :void
	    +getProducts() : List
	    +addProducts(product: Product) :void
	    +calculateTotal() : double
    }

    class PersonRepository {
	    +save(person:Person) : void
	    +load() : List
	    +saveAll(people: List) :void
	    +loadAll() : List
    }

    class ProductRepository {
	    +save(product:Product) : void
	    +load() : List
	    +saveAll(products: List) :void
	    +loadAll() : List
    }

    class SaleRepository {
	    +save(sale: Sale) : void
	    +load() : List
	    +saveAll(sales: List) :void
	    +loadAll() : List
    }

    class PersonService {
      -personRepository: PersonRepository
      +registerCustomer(customer: Customer): void
      +registerSeller(seller: Seller): void
      +listCustomers(): List
      +listSellers(): List
    }

    class ProductService {
      -productRepository: ProductRepository
      +registerProduct(product: Product): void
      +listProducts(): List
      +updateStock(product: Product, quantity: int): void
    }

    class SaleService {
      -saleRepository: SaleRepository
      +registerSale(sale: Sale): void
      +listSales(): List
      +getCustomerPurchaseHistory(customer: Customer): List
      +getSellerSalesHistory(seller: Seller): List
    }

    class ConsoleUI {
      -personService: PersonService
      -productService: ProductService
      -saleService: SaleService
      +showMainMenu(): void
      +showPersonMenu(): void
      +showProductMenu(): void
      +showSaleMenu(): void
      +start(): void
    }

    class Main {
      +main(args: String[]): void
    }
	<<abstract>> Person
	<<abstract>> Product

    Person <|-- Customer
    Person <|-- Seller
    Product <|-- Console
    Product <|-- VideoGame
    Sale "1" --> "1..*" Product : Products
    PersonRepository ..> Person
    ProductRepository ..> Product
    SaleRepository ..> Sale
    PersonService ..> PersonRepository
    PersonService ..> Person

    ProductService ..> ProductRepository
    ProductService ..> Product

    SaleService ..> SaleRepository
    SaleService ..> Sale
    SaleService ..> Product
    SaleService ..> Customer
    SaleService ..> Seller

    ConsoleUI ..> PersonService
    ConsoleUI ..> ProductService
    ConsoleUI ..> SaleService
    Main ..> ConsoleUI