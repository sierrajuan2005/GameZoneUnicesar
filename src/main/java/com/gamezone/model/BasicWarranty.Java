package com.gamezone.model;
import java.time.LocalDate;

public class BasicWarranty extends Warranty{

    public BasicWarranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate) {
        super(warrantyIdentifier, associatedProduct, associatedSale, startDate, endDate);
    }

    @Override
    public int getDurationInMonths() {
        return 6;
    }

    @Override
    public String getWarrantyType() {
        return "Basic Warranty";
    }

    @Override
    public double getAdditionalCost() {
        return 0.0;
    }
}