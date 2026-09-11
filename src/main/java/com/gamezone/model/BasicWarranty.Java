package com.gamezone.model;
import java.time.LocalDate;

public class BasicWarranty extends Warranty{
    private static final int DurationInMonths = 6;
    private static final String WarrantyType = "Basic Warranty";
    private static final double AdditionalCost = 0.0;

    public BasicWarranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate) {
        super(warrantyIdentifier, associatedProduct, associatedSale, startDate, endDate);
    }

    
}