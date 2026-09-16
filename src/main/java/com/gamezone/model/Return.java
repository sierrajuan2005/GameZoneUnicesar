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

    public String getIdentifier() {
        return identifier;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public Sale getOriginalSale() {
        return originalSale;
    }
    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }
    public String getReason() {
        return reason;
    }
    public double getRefundAmount() {
        return refundAmount;
    }
}
