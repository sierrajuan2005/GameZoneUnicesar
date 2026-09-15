package com.gamezone.model;
import java.time.LocalDate;

public class BasicWarranty extends Warranty{

    public BasicWarranty(String identifier, Product product, Sale sale, LocalDate startDate) {
        super(identifier, product, sale, startDate);
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