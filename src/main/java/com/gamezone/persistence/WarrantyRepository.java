package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Warranty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WarrantyRepository {

    private static final String FILE_PATH = "data/warranties.csv";

    private List<Warranty> warranties;
    private SaleRepository saleRepository;
    private ProductRepository productRepository;

    public WarrantyRepository(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.warranties = new ArrayList<>();
    }

    public void addWarranty(Warranty warranty){
        warranties.add(warranty);
        saveAll(warranties);
    }

    public List<Warranty> getAllWarranties(){
        return  new ArrayList<>(warranties);
    }

    public Warranty findByIdentifier(String identifier){
        for (Warranty w : warranties){
            if (w.getWarrantyIdentifier().equals(identifier)){
                return w;
            }
        }
        return null;
    }

    private String convertToCsv (Warranty w){

        if (w instanceof BasicWarranty b){
         return "BASIC;" + b.getWarrantyIdentifier() + ";" +
         b.getAssociatedProduct().getIdentifier() + ";" +
         b.getAssociatedSale().getIdentifier() + ";" +
         b.getStartDate() + ";" + b.getEndDate();
        } else if (w instanceof ExtendedWarranty e) {
            return "EXTENDED;" +e.getWarrantyIdentifier() + ";" +
                    e.getAssociatedProduct().getIdentifier() + ";" +
                    e.getAssociatedSale().getIdentifier() + ";" +
                    e.getStartDate() + ";" + e.getEndDate();
        }
        return "";
    }

    private Warranty convertFromCsv(String line){
        String[] data= line.split(";");
        String type = data[0];
        String warrantyIdentifier = data[1];
        String productIdentifier = data[2];
        String saleIdentifier = [3];
        LocalDate startDate = LocalDate.parse(data[4]);
        LocalDate endDate = LocalDate.parse(data[5]);

        Product product = productRepository.findByIdentifier(productIdentifier);
        Sale sale = saleRepository.findByIdentifier(saleIdentifier);
        if ("BASIC".equals(type)) {
            return new BasicWarranty(warrantyIdentifier, product, sale, startDate, endDate);
        } else if ("EXTENDED".equals(type)) {
            return new ExtendedWarranty(warrantyIdentifier, product, sale, startDate, endDate)
        }
        return null;
    }

    private void loadAll(){
        Path path = Paths.get(FILE_PATH);

        try {
            if (!Files.exists(path)){
                warranties.clear();
                return;
            }

            List<String>lines  = Files.readAllLines(path);
            warranties.clear();

            for (String line : lines){
                Warranty warranty = convertFromCsv(line);
                if (warranty != null){
                    warranties.add(warranty);
                }
            }
        }
        catch (IOException e){
            throw new RuntimeException("Error loading warranties.", e);
        }
    }



}
