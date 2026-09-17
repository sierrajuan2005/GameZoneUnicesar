package com.gamezone.persistence;

import com.gamezone.model.Return;
import com.gamezone.model.Warranty;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
}
