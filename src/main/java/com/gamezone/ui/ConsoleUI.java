package com.gamezone.ui;
import com.gamezone.model.*;
import com.gamezone.services.*;

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
    private final WarrantyService warrantyService;
    private final ReturnService returnService;
    private final PromotionService promotionService;
    private final AccessoryService accessoryService;

    /**
     * Creates the console user interface.
     *
     * @param personService service used to manage customers and sellers
     * @param productService service used to manage products
     * @param saleService service used to manage sales
     */
    public ConsoleUI(PersonService personService, ProductService productService, AccessoryService accessoryService, SaleService saleService,
                     WarrantyService warrantyService, ReturnService returnService, PromotionService promotionService)  {
        this.personService = personService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.saleService = saleService;
        this.warrantyService = warrantyService;
        this.returnService = returnService;
        this.promotionService = promotionService;
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
                case "4" -> showAccessoryMenu();
                case "5" -> showWarrantyMenu();
                case "6" -> showReturnMenu();
                case "7" -> showPromotionMenu();
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
        System.out.println("4. Manage accessories");
        System.out.println("5. Manage warranties");
        System.out.println("6. Manage returns");
        System.out.println("7. Manage promotions");
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
        System.out.println("5. Ver detalle de una venta específica");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerSale();
            case "2" -> listAllSales();
            case "3" -> showCustomerHistory();
            case "4" -> showSellerHistory();
            case "5" -> showSaleDetail();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void showSaleDetail() {
        System.out.print("Sale ID: ");
        String saleId = scanner.nextLine();

        Sale sale = saleService.findSaleById(saleId);
        if (sale == null) {
            System.out.println("No sale was found with that ID.");
            return;
        }
        System.out.println(sale.generateReceipt());
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

        List<String> productIdsWithExtendedWarranty = askExtendedWarranties(products);
        String saleId = "SALE-" + System.currentTimeMillis();

        Sale sale = new Sale(saleId, LocalDate.now(), customer, seller, products);

        try {
            saleService.registerSale(sale, productIdsWithExtendedWarranty);

            double extendedWarrantyCost = 0.0;

            for (Product product : products) {
                if (productIdsWithExtendedWarranty.contains(product.getIdentifier())) {
                    extendedWarrantyCost += product.getPrice() * 0.10;
                }
            }

            System.out.println("Sale registered successfully. Total: "
                    + (sale.calculateTotal() + extendedWarrantyCost));

        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("The sale could not be registered: " + e.getMessage());
        }
    }

    private List<String> askExtendedWarranties(List<Product> products) {
        List<String> selected = new ArrayList<>();

        for (Product product : products) {
            if (!(product instanceof Console)) {
                continue;
            }

            System.out.print("Would you like to add an extended warranty for "
                    + product.getTitle()
                    + " (additional cost of 10%)? (y/n): ");

            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                selected.add(product.getIdentifier());
            }
        }

        return selected;
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

    /**
     * Shows the warranty submenu and handles its options.
     */
    public void showWarrantyMenu() {
        System.out.println("\n--- Warranty Management ---");
        System.out.println("1. Check a product's warranty in a sale");
        System.out.println("2. List all registered warranties");
        System.out.println("3. List active warranties");
        System.out.println("4. List warranties expiring soon");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> findWarranty();
            case "2" -> listAllWarranties();
            case "3" -> listActiveWarranties();
            case "4" -> listWarrantiesExpiringSoon();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void findWarranty() {
        System.out.print("Product identifier: ");
        String productId = scanner.nextLine();

        System.out.print("Sale identifier: ");
        String saleId = scanner.nextLine();

        Warranty warranty = warrantyService.findWarrantyByProduct(productId, saleId);

        if (warranty == null) {
            System.out.println("No warranty was found for that product in that sale.");
            return;
        }

        System.out.println(warranty.generateWarrantyCertificate());
    }

    private void listAllWarranties() {
        printWarranties(warrantyService.listWarranties());
    }

    private void listActiveWarranties() {
        printWarranties(warrantyService.listActiveWarranties());
    }

    private void listWarrantiesExpiringSoon() {
        try {
            System.out.print("How many days in advance would you like to check? ");
            int daysAhead = Integer.parseInt(scanner.nextLine());

            printWarranties(warrantyService.listWarrantiesExpiringSoon(daysAhead));

        } catch (NumberFormatException e) {
            System.out.println("Error: you must enter a valid number of days.");
        }
    }

    private void printWarranties(List<Warranty> warranties) {
        if (warranties.isEmpty()) {
            System.out.println("No warranties were found.");
            return;
        }

        for (Warranty warranty : warranties) {
            System.out.println(warranty.generateWarrantyCertificate());
            System.out.println("---");
        }
    }

    /**
     * Shows the return submenu and handles its options.
     */
    public void showReturnMenu() {
        System.out.println("\n--- Manage Returns ---");
        System.out.println("1. Register a return");
        System.out.println("2. View all returns");
        System.out.println("3. View returns by customer");
        System.out.println("4. View returns by sale");
        System.out.println("5. View monthly balance");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerReturn();
            case "2" -> listAllReturns();
            case "3" -> listReturnsByCustomer();
            case "4" -> listReturnsBySale();
            case "5" -> showMonthlyBalance();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void registerReturn() {
        System.out.print("Sale identifier: ");
        String saleId = scanner.nextLine();

        Sale sale = saleService.findSaleById(saleId);

        if (sale == null) {
            System.out.println("No sale was found with that identifier.");
            return;
        }

        System.out.println("Products in the sale:");

        for (Product product : sale.getProducts()) {
            System.out.println("- " + product.getIdentifier() + ": " + product.getDescription());
        }

        List<String> productIds = new ArrayList<>();
        String identifier;

        System.out.println("Enter the identifiers of the products to return. Leave empty to finish.");

        do {
            System.out.print("Product identifier (empty to finish): ");
            identifier = scanner.nextLine();

            if (!identifier.isBlank()) {
                productIds.add(identifier);
            }

        } while (!identifier.isBlank());

        if (productIds.isEmpty()) {
            System.out.println("You must specify at least one product to return. Operation cancelled.");
            return;
        }

        System.out.print("Reason for the return: ");
        String reason = scanner.nextLine();

        try {
            Return returnItem = returnService.registerReturn(saleId, productIds, reason);
            System.out.println(returnItem.generateReturnReceipt());

        } catch (IllegalArgumentException e) {
            System.out.println("The return could not be registered: " + e.getMessage());
        }
    }

    private void listAllReturns() {
        printReturns(returnService.viewAllReturns());
    }

    private void listReturnsByCustomer() {
        System.out.print("Customer identification: ");
        String customerId = scanner.nextLine();

        printReturns(returnService.viewReturnsByCustomer(customerId));
    }

    private void listReturnsBySale() {
        System.out.print("Sale identifier: ");
        String saleId = scanner.nextLine();

        printReturns(returnService.viewReturnsBySale(saleId));
    }

    private void showMonthlyBalance() {
        try {
            System.out.print("Month (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());

            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            double balance = returnService.generateMonthlyBalance(month, year);

            System.out.println("Net balance for " + month + "/" + year + ": $" + balance);

        } catch (NumberFormatException e) {
            System.out.println("Error: month and year must be valid numeric values.");

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void printReturns(List<Return> returns) {
        if (returns.isEmpty()) {
            System.out.println("No returns were found.");
            return;
        }

        for (Return returnItem : returns) {
            System.out.println(returnItem.generateReturnReceipt());
            System.out.println("---");
        }
    }

    /**
     * Shows the promotion submenu and handles its options.
     */
    public void showPromotionMenu() {
        System.out.println("\n--- Promotion Management ---");
        System.out.println("1. Register percentage-based promotion");
        System.out.println("2. Register category-based promotion");
        System.out.println("3. Register bulk purchase promotion");
        System.out.println("4. List all promotions");
        System.out.println("5. List active promotions");
        System.out.println("0. Back");
        System.out.print("Choose an option: ");

        switch (scanner.nextLine()) {
            case "1" -> registerPercentagePromotion();
            case "2" -> registerCategoryPromotion();
            case "3" -> registerBulkPromotion();
            case "4" -> listAllPromotions();
            case "5" -> listActivePromotions();
            case "0" -> { }
            default -> System.out.println("Invalid option.");
        }
    }

    private void registerPercentagePromotion() {
        try {
            System.out.print("Identifier: ");
            String id = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("End date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Discount percentage (0-100): ");
            double percentage = Double.parseDouble(scanner.nextLine());

            promotionService.registerPercentageDiscount(
                    id, name, startDate, endDate, percentage
            );

            System.out.println("Promotion registered successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: the percentage must be a valid numeric value.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registerCategoryPromotion() {
        try {
            System.out.print("Identifier: ");
            String id = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("End date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Discount percentage (0-100): ");
            double percentage = Double.parseDouble(scanner.nextLine());

            System.out.print("Target category (VIDEOGAME/CONSOLE): ");
            String targetCategory = scanner.nextLine();

            promotionService.registerCategoryDiscount(
                    id, name, startDate, endDate, percentage, targetCategory
            );

            System.out.println("Promotion registered successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: the percentage must be a valid numeric value.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registerBulkPromotion() {
        try {
            System.out.print("Identifier: ");
            String id = scanner.nextLine();

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Start date (YYYY-MM-DD): ");
            LocalDate startDate = LocalDate.parse(scanner.nextLine());

            System.out.print("End date (YYYY-MM-DD): ");
            LocalDate endDate = LocalDate.parse(scanner.nextLine());

            System.out.print("Minimum number of products: ");
            int minimumQuantity = Integer.parseInt(scanner.nextLine());

            System.out.print("Discount percentage (0-100): ");
            double percentage = Double.parseDouble(scanner.nextLine());

            promotionService.registerBulkPurchaseDiscount(
                    id, name, startDate, endDate, minimumQuantity, percentage
            );

            System.out.println("Promotion registered successfully.");

        } catch (NumberFormatException e) {
            System.out.println("Error: quantity and percentage must be valid numeric values.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllPromotions() {
        printPromotions(promotionService.listAllPromotions());
    }

    private void listActivePromotions() {
        printPromotions(promotionService.listActivePromotions());
    }

    private void printPromotions(List<Promotion> promotions) {
        if (promotions.isEmpty()) {
            System.out.println("No promotions were found.");
            return;
        }

        for (Promotion promotion : promotions) {
            System.out.println(
                    promotion.getIdentifier() + " - "
                            + promotion.getName()
                            + " (" + promotion.getStartDate()
                            + " to " + promotion.getEndDate() + ")"
            );
        }
    }


    private void showAccessoryMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== GESTIÓN DE ACCESORIOS ===");
            System.out.println("1. Registrar controlador");
            System.out.println("2. Registrar cable");
            System.out.println("3. Registrar memoria");
            System.out.println("4. Listar todos los accesorios");
            System.out.println("5. Listar accesorios por tipo");
            System.out.println("6. Buscar accesorios compatibles con consola");
            System.out.println("0. Volver");

            System.out.print("Seleccione una opción: ");

            switch (scanner.nextLine()) {
                case "1" -> registerController();
                case "2" -> registerCable();
                case "3" -> registerMemory();
                case "4" -> listAllAccessories();
                case "5" -> listAccessoriesByType();
                case "6" -> listCompatibleAccessories();
                case "0" -> running = false;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void registerController() {
        System.out.println("\n=== REGISTRAR CONTROLADOR ===");

        System.out.print("Identificador: ");
        String id = scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad disponible: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Tipo de conexión: ");
        String connectionType = scanner.nextLine();

        List<Console> consoles = selectCompatibleConsoles();

        try {
            accessoryService.registerController(
                    id,
                    title,
                    price,
                    quantity,
                    consoles,
                    connectionType
            );

            System.out.println("Controlador registrado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar el controlador: "
                    + e.getMessage());
        }
    }

    private List<Console> selectCompatibleConsoles() {
        List<Console> consoles = new ArrayList<>();

        List<Product> products = productService.listProducts();

        System.out.println("\n=== CONSOLAS DISPONIBLES ===");

        for (Product product : products) {
            if (product instanceof Console console) {
                System.out.println(
                        console.getIdentifier() + " - "
                                + console.getTitle()
                );
            }
        }

        System.out.println("Ingrese los identificadores de las consolas compatibles.");
        System.out.println("Deje vacío para terminar.");

        while (true) {
            System.out.print("ID de consola: ");
            String consoleId = scanner.nextLine();

            if (consoleId.isBlank()) {
                break;
            }

            Console console = findConsoleById(products, consoleId);

            if (console != null) {
                consoles.add(console);
                System.out.println("Consola agregada: " + console.getTitle());
            } else {
                System.out.println("No se encontró una consola con ese identificador.");
            }
        }

        return consoles;
    }

    private Console findConsoleById(List<Product> products, String identifier) {
        for (Product product : products) {
            if (product instanceof Console console
                    && console.getIdentifier().equals(identifier)) {
                return console;
            }
        }

        return null;
    }

    private void registerCable() {
        System.out.println("\n=== REGISTRAR CABLE ===");

        System.out.print("Identificador: ");
        String id = scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad disponible: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Longitud en metros: ");
        double length = Double.parseDouble(scanner.nextLine());

        System.out.print("Tipo de conector: ");
        String connectorType = scanner.nextLine();

        List<Console> consoles = selectCompatibleConsoles();

        try {
            accessoryService.registerCable(
                    id,
                    title,
                    price,
                    quantity,
                    consoles,
                    length,
                    connectorType
            );

            System.out.println("Cable registrado correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar el cable: "
                    + e.getMessage());
        }
    }

    private void registerMemory() {
        System.out.println("\n=== REGISTRAR MEMORIA ===");

        System.out.print("Identificador: ");
        String id = scanner.nextLine();

        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Precio: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Cantidad disponible: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Capacidad en GB: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Tipo de memoria: ");
        String memoryType = scanner.nextLine();

        List<Console> consoles = selectCompatibleConsoles();

        try {
            accessoryService.registerMemory(
                    id,
                    title,
                    price,
                    quantity,
                    consoles,
                    capacity,
                    memoryType
            );

            System.out.println("Memoria registrada correctamente.");

        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo registrar la memoria: "
                    + e.getMessage());
        }
    }

    private void listAllAccessories() {
        System.out.println("\n=== TODOS LOS ACCESORIOS ===");

        List<Accessory> accessories = accessoryService.listAllAccessories();

        if (accessories.isEmpty()) {
            System.out.println("No hay accesorios registrados.");
            return;
        }

        for (Accessory accessory : accessories) {
            System.out.println(
                    accessory.getIdentifier()
                            + " | "
                            + accessory.getDescription()
                            + " | Precio: $"
                            + accessory.getPrice()
                            + " | Stock: "
                            + accessory.getAvailableQuantity()
            );
        }
    }

    private void listAccessoriesByType() {
        System.out.println("\n=== ACCESORIOS POR TIPO ===");
        System.out.println("Tipos disponibles: Controller, Cable, Memory");

        System.out.print("Ingrese el tipo: ");
        String type = scanner.nextLine();

        List<Accessory> accessories = accessoryService.listAccessoriesByType(type);

        if (accessories.isEmpty()) {
            System.out.println("No se encontraron accesorios de ese tipo.");
            return;
        }

        for (Accessory accessory : accessories) {
            System.out.println(
                    accessory.getIdentifier()
                            + " | "
                            + accessory.getDescription()
                            + " | Stock: "
                            + accessory.getAvailableQuantity()
            );
        }
    }

    private void listCompatibleAccessories() {
        System.out.println("\n=== ACCESORIOS COMPATIBLES ===");

        List<Product> products = productService.listProducts();

        System.out.println("=== CONSOLAS ===");

        for (Product product : products) {
            if (product instanceof Console console) {
                System.out.println(
                        console.getIdentifier()
                                + " - "
                                + console.getTitle()
                );
            }
        }

        System.out.print("Ingrese el ID de la consola: ");
        String consoleId = scanner.nextLine();

        List<Accessory> accessories =
                accessoryService.findAccessoriesCompatibleWith(consoleId);

        if (accessories.isEmpty()) {
            System.out.println(
                    "No se encontraron accesorios compatibles con esa consola."
            );
            return;
        }

        System.out.println("\nAccesorios compatibles:");

        for (Accessory accessory : accessories) {
            System.out.println(
                    accessory.getIdentifier()
                            + " | "
                            + accessory.getDescription()
            );
        }
    }

}
