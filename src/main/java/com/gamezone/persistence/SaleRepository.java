package com.gamezone.persistence;
import com.gamezone.model.Console;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.model.VideoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository responsible for saving and loading sales from a text file.
 */
public class SaleRepository {

    private static final String FILE_PATH = "data/sales.txt";

    /**
     * Saves a new sale.
     *
     * @param sale sale to save
     */
    public void save(Sale sale) {
        List<Sale> sales = loadAll();
        sales.add(sale);
        saveAll(sales);
    }

    /**
     * Loads all sales from the file.
     *
     * @return list of sales
     */
    public List<Sale> load() {
        return loadAll();
    }

    /**
     * Saves all sales to the file.
     *
     * @param sales list of sales to save
     */
    public void saveAll(List<Sale> sales) {
        Path path = Paths.get(FILE_PATH);

        try {
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();

            for (Sale sale : sales) {
                lines.add(convertToCsv(sale));
            }

            Files.write(path, lines);

        } catch (IOException e) {
            throw new RuntimeException("Error saving sales.", e);
        }
    }

    /**
     * Loads all sales stored in the file.
     *
     * @return list of sales
     */
    public List<Sale> loadAll() {
        Path path = Paths.get(FILE_PATH);
        List<Sale> sales = new ArrayList<>();

        if (!Files.exists(path)) {
            return sales;
        }

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                if (!line.isBlank()) {
                    Sale sale = convertFromCsv(line);
                    sales.add(sale);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error loading sales.", e);
        }

        return sales;
    }

    private String convertToCsv(Sale sale) {
        Customer customer = sale.getCustomer();
        Seller seller = sale.getSeller();

        return sale.getDate() + ";"
                + customer.getName() + ";"
                + customer.getIdentification() + ";"
                + customer.getPhone() + ";"
                + customer.getEmail() + ";"
                + seller.getName() + ";"
                + seller.getIdentification() + ";"
                + seller.getPhone() + ";"
                + seller.getEmployeeCode() + ";"
                + seller.getWorkShift() + ";"
                + convertProductsToCsv(sale.getProducts());
    }

    private Sale convertFromCsv(String line) {

        String[] data = line.split(";");

        LocalDate date = LocalDate.parse(data[0]);
        Customer customer = new Customer(data[1], data[2], data[3], data[4]);
        Seller seller = new Seller(data[5], data[6], data[7], data[8], data[9]);
        List<Product> products = convertProductsFromCsv(data[10]);

        return new Sale(date, customer, seller, products);
    }

    private String convertProductsToCsv(List<Product> products) {
        List<String> productParts = new ArrayList<>();

        for (Product product : products) {
            productParts.add(convertProductToCsv(product));
        }

        return String.join("|", productParts);
    }

    private List<Product> convertProductsFromCsv(String data) {
        List<Product> products = new ArrayList<>();

        for (String productData : data.split("\\|")) {
            products.add(convertProductFromCsv(productData));
        }

        return products;
    }

    private String convertProductToCsv(Product product) {

        if (product instanceof VideoGame videoGame) {
            return "VIDEO_GAME,"
                    + videoGame.getIdentifier() + ","
                    + videoGame.getTitle() + ","
                    + videoGame.getPrice() + ","
                    + videoGame.getAvailableQuantity() + ","
                    + videoGame.getPlatform() + ","
                    + videoGame.getGenre() + ","
                    + videoGame.getAgeRating();
        }

        if (product instanceof Console console) {
            return "CONSOLE,"
                    + console.getIdentifier() + ","
                    + console.getTitle() + ","
                    + console.getPrice() + ","
                    + console.getAvailableQuantity() + ","
                    + console.getBrand() + ","
                    + console.getModel() + ","
                    + console.getGeneration();
        }

        throw new IllegalArgumentException("Unsupported product type.");
    }

    private Product convertProductFromCsv(String data) {

        String[] fields = data.split(",");
        String type = fields[0];

        if (type.equals("VIDEO_GAME")) {
            return new VideoGame(
                    fields[1],
                    fields[2],
                    Double.parseDouble(fields[3]),
                    Integer.parseInt(fields[4]),
                    fields[5],
                    fields[6],
                    fields[7]
            );
        }

        if (type.equals("CONSOLE")) {
            return new Console(
                    fields[1],
                    fields[2],
                    Double.parseDouble(fields[3]),
                    Integer.parseInt(fields[4]),
                    fields[5],
                    fields[6],
                    fields[7]
            );
        }

        throw new IllegalArgumentException("Unknown product type: " + type);
    }
}