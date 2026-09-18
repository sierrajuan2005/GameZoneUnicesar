package com.gamezone.model;
import java.time.LocalDate;

public class ExtendedWarranty extends Warranty {

    public ExtendedWarranty(String identifier, Product product, Sale sale, LocalDate startDate) {
        super(identifier, product, sale, startDate);
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
        return getProduct().getPrice() * 0.10;
    }
}