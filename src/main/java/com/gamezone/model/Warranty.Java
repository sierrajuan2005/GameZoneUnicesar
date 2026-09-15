package com.gamezone.model;
import java.time.LocalDate;

public abstract class Warranty{
    private String id;
    private Product product;
    private Sale sale;
    private LocalDate startDate;
    private LocalDate endDate;

    public Warranty(String id, Product product, Sale sale, LocalDate startDate){
        this.id = id;
        this.product = product;
        this.sale = sale;
        this.startDate = startDate;
        this.endDate = startDate.plusMonths(getDurationInMonths());
    }
    
    public String getId() {
        return id;
    }
    

    public Product getProduct() {
        return product;
    }

    public Sale getSale() {
        return sale;
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


    public boolean isActive(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public String generateWarrantyCertificate(){
        return "Certificado de Garantía\n" +
            "ID: " + id + "\n" +
            "Tipo: " + getWarrantyType() + "\n" +
            "Producto: " + product.getTitle() + "\n" +
            "Fecha de inicio: " + startDate + "\n" +
            "Fecha de vencimiento: " + endDate + "\n" +
            "Duración (meses): " + getDurationInMonths() + "\n" +
            "Costo adicional: $" + getAdditionalCost();
    }
}