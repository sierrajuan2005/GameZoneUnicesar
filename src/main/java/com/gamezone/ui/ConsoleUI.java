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


public class ConsoleUI {

    private final PersonService personService;
    private final ProductService productService;
    private final SaleService saleService;
    private final Scanner scanner;

    /**
     * Creates the console user interface.
     *
     * @param personService  service used to manage customers and sellers
     * @param productService service used to manage products
     * @param saleService    service used to manage sales
     */
    public ConsoleUI(PersonService personService, ProductService productService, SaleService saleService) {
        this.personService = personService;
        this.productService = productService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }


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


    public void showMainMenu() {
        System.out.println("\n===== GameZone Unicesar =====");
        System.out.println("1. Manage products");
        System.out.println("2. Manage customers and sellers");
        System.out.println("3. Manage sales");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

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



}


