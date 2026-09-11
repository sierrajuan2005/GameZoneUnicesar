package com.gamezone.model;
import java.time.LocalDate;

public class ExtendedWarranty extends Warranty{
    private static final int DurationInMonths = 12;
    private static final String WarrantyType = "Extended Warranty";
    private static final double AdditionalCost;

    public ExtendedWarranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate) {
        super(warrantyIdentifier, associatedProduct, associatedSale, startDate, endDate);
    }

}