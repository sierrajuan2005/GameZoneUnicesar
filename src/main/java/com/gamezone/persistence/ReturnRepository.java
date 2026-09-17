package com.gamezone.persistence;

import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;

public class ReturnRepository {

    private static final String FILE_PATH = "data/returns.csv";
    private final SaleService saleService;
    private final ProductService productService;

    public ReturnRepository(SaleService saleService, ProductService productService) {
        this.saleService = saleService;
        this.productService = productService;
    }
}
