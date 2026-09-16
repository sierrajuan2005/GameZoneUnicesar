package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

public class Return {
    private String identifier;
    private LocalDate returnDate;
    private Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    public Return(String identifier, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String reason) {
        this.identifier = identifier;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = 0.0;
    }
}
