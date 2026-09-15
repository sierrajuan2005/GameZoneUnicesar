package com.gamezone.persistence;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.ExtendedWarranty;
import com.gamezone.model.Product;
import com.gamezone.model.Warranty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return warranties;
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



}
