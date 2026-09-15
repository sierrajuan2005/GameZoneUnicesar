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
        this.endDate= startDate.plusMonths(getDurationInMonths());
    }
    
    public String getWarrantyIdentifier() {
        return warrantyIdentifier;
    }
    

    public Product getAssociatedProduct() {
        return associatedProduct;
    }

    public Sale getAssociatedSale() {
        return associatedSale;
    }
    

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract int getDurationInMonths();

    public abstract String getWarrantyType();

    public abstract double getAdditionalCost();
    

    public boolean getIsActive(){
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public String generateWarrantyCertificate(){
        return "Warranty Certificate\n" +
                "Warranty Identifier: " + warrantyIdentifier + "\n" +
                "Warranty Type: " + getWarrantyType() + "\n" +
                "Associated Product: " + associatedProduct.getProductName() + "\n" +
                "Associated Sale: " + associatedSale.getSaleIdentifier() + "\n" +
                "Start Date: " + startDate.toString() + "\n" +
                "End Date: " + endDate.toString() + "\n" +
                "Duration (Months): " + getDurationInMonths() + "\n" +
                "Additional Cost: $" + getAdditionalCost();
    }
}