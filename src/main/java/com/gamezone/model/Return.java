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

}
