package com.gamezone.persistence;
import com.gamezone.domain.Console;
import com.gamezone.domain.Customer;
import com.gamezone.domain.Product;
import com.gamezone.domain.Sale;
import com.gamezone.domain.Seller;
import com.gamezone.domain.VideoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



public class SaleRepository {

    private static final String FILE_PATH = "data/sales.txt";

    private PersonRepository personRepository;
    private ProductRepository productRepository;

    public SaleRepository(PersonRepository personRepository, ProductRepository productRepository) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
    }


    public void save(Sale sale) {
        List<Sale> sales = loadAll();
        sales.add(sale);
        saveAll(sales);
    }

    public List<Sale> load() {
        return loadAll();
    }


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










}
