package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Repository class responsible for persisting and loading {@link Return} records
 * to and from a CSV file storage.
 */
public class ReturnRepository {

    private static final String FILE_PATH = "data/returns.csv";
    private final SaleService saleService;
    private final ProductService productService;

    /*
     * Constructs a ReturnRepository with required service dependencies.
     *
     * @param saleService    service used to reconstruct original sales
     * @param productService service used to reconstruct returned products
     */
    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    /*
     * Saves all given return records to the CSV file.
     *
     * @param returns list of {@link Return} objects to persist
     * @throws RuntimeException if an I/O error occurs during saving
     */
    public void saveAll(List<Return> returns){
        Path path = Paths.get(FILE_PATH);
        List<String> lines = new ArrayList<>();

        for (Return r : returns){
            lines.add(convertToCsv(r));
        }
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, lines);
        }catch (IOException e){
            throw new RuntimeException("Error saving returns.", e);
        }
    }

    /*
     * Loads all return records from the CSV file.
     *
     * @return list of loaded {@link Return} objects, or an empty list if the file does not exist
     * @throws RuntimeException if an I/O error occurs during loading
     */
    public List<Return> loadAll() {
        Path path = Paths.get(FILE_PATH);
        List<Return> returns = new ArrayList<>();
        if (!Files.exists(path)) {
            return returns;
        }
        try {
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.isBlank()) {
                    Return r = convertFromCsv(line);
                    if (r != null) {
                        returns.add(r);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading returns.", e);
        }
        return returns;
    }

    /*
     * Converts a {@link Return} object into a CSV-formatted string line.
     *
     * @param r the return entity to serialize
     * @return CSV formatted string representing the return
     */
    private String convertToCsv(Return r) {
        StringBuilder sb = new StringBuilder();
        sb.append(r.getIdentifier()).append(";")
                .append(r.getReturnDate()).append(";")
                .append(r.getOriginalSale() != null ? r.getOriginalSale().getIdentifier() : "").append(";")
                .append(r.getReason()).append(";")
                .append(r.getRefundAmount()).append(";");

        List<String> productIds = new ArrayList<>();
        if (r.getReturnedProducts() != null) {
            for (Product p : r.getReturnedProducts()) {
                productIds.add(p.getIdentifier());
            }
        }
        sb.append(String.join(",", productIds));

        return sb.toString();
    }

    /*
     * Reconstructs a {@link Return} object from a single CSV line.
     *
     * @param line CSV text line containing return data
     * @return deserialized {@link Return} instance, or {@code null} if line format is invalid
     */
    private Return convertFromCsv(String line) {
        String[] data = line.split(";", -1);
        if (data.length < 5) return null;

        String identifier = data[0];
        LocalDate returnDate = LocalDate.parse(data[1]);
        Sale sale = saleService.findSaleById(data[2]);
        String reason = data[3];

        List<Product> returnedProducts = new ArrayList<>();
        if (data.length >= 6 && !data[5].isBlank()) {
            String[] pIds = data[5].split(",");
            for (String pId : pIds) {
                Product product = productService.findByIdentifier(pId);
                if (product != null) {
                    returnedProducts.add(product);
                }
            }
        }

        return new Return(identifier, returnDate, sale, returnedProducts, reason);
    }

}
