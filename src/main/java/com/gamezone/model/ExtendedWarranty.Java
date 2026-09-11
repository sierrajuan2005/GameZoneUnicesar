package com.gamezone.model;
import java.time.LocalDate;

public class ExtendedWarranty extends Warranty{
    private static final int DurationInMonths = 12;
    private static final String WarrantyType = "Extended Warranty";
    private static final double AdditionalCost;

    public ExtendedWarranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate) {
        super(warrantyIdentifier, associatedProduct, associatedSale, startDate, endDate);
    }

    @Override
    public int getDurationInMonths() {
        return DurationInMonths;
    }

    @Override
    public String getWarrantyType() {
        return WarrantyType;
    }

    @Override
    public double getAdditionalCost() {
        return 0.1*super.getAssociatedProduct().getPrice();
    }
}