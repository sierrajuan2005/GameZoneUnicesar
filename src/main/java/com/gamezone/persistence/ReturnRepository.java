package com.gamezone.persistence;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.model.Warranty;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnRepository {

    private static final String FILE_PATH = "data/returns.csv";
    private final SaleService saleService;
    private final ProductService productService;

    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }

    private  void saveAll(List<Return> returns){
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

    private String convertToCsv(Return r) {
        StringBuilder sb = new StringBuilder();
        sb.append(r.getIdentifier()).append(";")
                .append(r.getReturnDate()).append(";")
                .append(r.getOriginalSale().toString()).append(";")
                .append(r.getReason()).append(";")
                .append(r.getRefundAmount());
        return sb.toString();
    }

    private Return convertFromCsv(String line) {
        String[] data = line.split(";");
        if (data.length < 5) return null;

        String identifier = data[0];
        LocalDate returnDate = LocalDate.parse(data[1]);
        Sale sale = saleService.findSaleById(data[2]);
        String reason = data[3];
        double refundAmount = Double.parseDouble(data[4]);

        List<Product> returnedProducts = new ArrayList<>();
        return new Return(identifier, returnDate, sale, returnedProducts, reason);
    }

}
