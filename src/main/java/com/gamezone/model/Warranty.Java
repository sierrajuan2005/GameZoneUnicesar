package com.gamezone.model;
import java.time.LocalDate;
import java.util.List;

public abstract class Warranty{
    private String warrantyIdentifier;
    private Product associatedProduct;
    private Sale associatedSale;
    private LocalDate startDate;
    private LocalDate endDate;

    public Warranty(String warrantyIdentifier, Product associatedProduct, Sale associatedSale, LocalDate startDate, LocalDate endDate){
        this.warrantyIdentifier = warrantyIdentifier;
        this.associatedProduct = associatedProduct;
        this.associatedSale = associatedSale;
        this.startDate = startDate;
    }
    
}