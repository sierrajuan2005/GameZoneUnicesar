package com.gamezone.ui;
import com.gamezone.model.Console;
import com.gamezone.model.Customer;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.model.VideoGame;
import com.gamezone.services.PersonService;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Provides the console interface for interacting with the GameZone system.
 */
public class ConsoleUI {

    private final PersonService personService;
    private final ProductService productService;
    private final SaleService saleService;
    private final Scanner scanner;

    /**
     * Creates the console user interface.
     *
     * @param personService service used to manage customers and sellers
     * @param productService service used to manage products
     * @param saleService service used to manage sales
     */
    public ConsoleUI(PersonService personService, ProductService productService, SaleService saleService) {
        this.personService = personService;
        this.productService = productService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the main menu of the application.
     */
    public void start() {
        boolean running = true;

        while (running) {
            showMainMenu();

            switch (scanner.nextLine()) {
                case "1" -> showProductMenu();
                case "2" -> showPersonMenu();
                case "3" -> showSaleMenu();
                case "0" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        System.out.println("Closing GameZone Unicesar. See you soon!");
    }

    /**
     * Displays the main application menu.
     */
    public void showMainMenu() {
        System.out.println("\n===== GameZone Unicesar =====");
        System.out.println("1. Manage products");
        System.out.println("2. Manage customers and sellers");
        System.out.println("3. Manage sales");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Displays the product management menu.
     */
    public void showProductMenu() {
        System.out.println("\n--- Product Menu ---");
        System.out.println("1. Register a video game");
        System.out.println("2. Register a console");
        System.out.println("3. List available products");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerVideoGame();
            case "2" -> registerConsole();
            case "3" -> listProducts();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void registerVideoGame() {
        try {
            System.out.print("Id: ");
            String id = scanner.nextLine();
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Available quantity: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Platform: ");
            String platform = scanner.nextLine();
            System.out.print("Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Age rating: ");
            String ageRating = scanner.nextLine();

            Product videoGame = new VideoGame(id, title, price, stock, platform, genre, ageRating);
            productService.registerProduct(videoGame);
            System.out.println("Video game registered successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: price and quantity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registerConsole() {
        try {
            System.out.print("Id: ");
            String id = scanner.nextLine();
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.print("Available quantity: ");
            int stock = Integer.parseInt(scanner.nextLine());
            System.out.print("Brand: ");
            String brand = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Generation: ");
            String generation = scanner.nextLine();

            Product console = new Console(id, title, price, stock, brand, model, generation);
            productService.registerProduct(console);
            System.out.println("Console registered successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: price and quantity must be valid numbers.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listProducts() {
        List<Product> products = productService.listProducts();

        if (products.isEmpty()) {
            System.out.println("There are no products registered yet.");
            return;
        }

        for (Product product : products) {
            System.out.println(product.getDescription() + " | Stock: " + product.getAvailableQuantity());
        }
    }

    /**
     * Displays the customer and seller management menu.
     */
    public void showPersonMenu() {
        System.out.println("\n--- Person Menu ---");
        System.out.println("1. Register a customer");
        System.out.println("2. List customers");
        System.out.println("3. List sellers");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerCustomer();
            case "2" -> listCustomers();
            case "3" -> listSellers();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void registerCustomer() {
        try {
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Identification: ");
            String identification = scanner.nextLine();
            System.out.print("Phone: ");
            String phone = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();

            Customer customer = new Customer(name, identification, phone, email);
            personService.addPerson(customer);
            System.out.println("Customer registered successfully.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listCustomers() {
        List<Person> people = personService.getAllPeople();
        boolean found = false;

        for (Person person : people) {
            if (person instanceof Customer) {
                System.out.println(person);
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are no customers registered yet.");
        }
    }

    private void listSellers() {
        List<Person> people = personService.getAllPeople();
        boolean found = false;

        for (Person person : people) {
            if (person instanceof Seller) {
                System.out.println(person);
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are no sellers registered yet.");
        }
    }

    /**
     * Displays the sales management menu.
     */
    public void showSaleMenu() {
        System.out.println("\n--- Sale Menu ---");
        System.out.println("1. Register a sale");
        System.out.println("2. List all sales");
        System.out.println("3. View a customer's purchase history");
        System.out.println("4. View sales handled by a seller");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerSale();
            case "2" -> listAllSales();
            case "3" -> showCustomerHistory();
            case "4" -> showSellerHistory();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void registerSale() {
        Customer customer = selectCustomer();

        if (customer == null) {
            return;
        }

        Seller seller = selectSeller();

        if (seller == null) {
            return;
        }

        List<Product> products = selectProducts();

        if (products.isEmpty()) {
            System.out.println("A sale must contain at least one product. Sale cancelled.");
            return;
        }

        Sale sale = new Sale(LocalDate.now(), customer, seller, products);

        try {
            saleService.registerSale(sale);
            System.out.println("Sale registered successfully. Total: " + sale.calculateTotal());

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Could not register the sale: " + e.getMessage());
        }
    }

    private Customer selectCustomer() {
        System.out.print("Customer identification: ");
        String identification = scanner.nextLine();

        Person person = personService.findPersonByIdentification(identification);

        if (person instanceof Customer) {
            return (Customer) person;
        }

        System.out.println("No customer found with that identification.");
        return null;
    }

    private Seller selectSeller() {
        System.out.print("Seller identification: ");
        String identification = scanner.nextLine();

        Person person = personService.findPersonByIdentification(identification);

        if (person instanceof Seller) {
            return (Seller) person;
        }

        System.out.println("No seller found with that identification.");
        return null;
    }

    private List<Product> selectProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        List<Product> availableProducts = productService.listProducts();
        String identifier;

        System.out.println("Enter product identifiers one at a time. Leave empty to finish.");

        do {
            System.out.print("Product identifier (empty to finish): ");
            identifier = scanner.nextLine();

            if (!identifier.isBlank()) {
                Product product = findProductById(availableProducts, identifier);

                if (product != null) {
                    selectedProducts.add(product);
                    System.out.println("Added: " + product.getDescription());
                } else {
                    System.out.println("No product found with that identifier.");
                }
            }

        } while (!identifier.isBlank());

        return selectedProducts;
    }

    private Product findProductById(List<Product> products, String identifier) {
        for (Product product : products) {
            if (product.getIdentifier().equals(identifier)) {
                return product;
            }
        }

        return null;
    }

    private void listAllSales() {
        printSales(saleService.listSales());
    }

    private void showCustomerHistory() {
        Customer customer = selectCustomer();

        if (customer == null) {
            return;
        }

        printSales(saleService.getCustomerPurchaseHistory(customer));
    }

    private void showSellerHistory() {
        Seller seller = selectSeller();

        if (seller == null) {
            return;
        }

        printSales(saleService.getSellerSalesHistory(seller));
    }

    private void printSales(List<Sale> sales) {
        if (sales.isEmpty()) {
            System.out.println("No sales were found.");
            return;
        }

        for (Sale sale : sales) {
            System.out.println(sale);
        }
    }
}
