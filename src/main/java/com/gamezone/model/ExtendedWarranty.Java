package com.gamezone.model;
import java.time.LocalDate;

public class ExtendedWarranty extends Warranty{

    public ExtendedWarranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate) {
        super(warrantyIdentifier, associatedProduct, associatedSale, startDate, endDate);
    }

    @Override
    public int getDurationInMonths() {
        return 12;
    }

    @Override
    public String getWarrantyType() {
        return "Extended Warranty" ;
    }

    @Override
    public double getAdditionalCost() {
        return getAssociatedProduct().getPrice() * 0.1;
    }
}