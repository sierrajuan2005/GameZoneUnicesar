package com.gamezone.persistence;

import com.gamezone.model.Warranty;

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

}
