package com.gamezone.domain;
public abstract class Product{
    private String identifier;
    private String title;
    private double price;
    private int availableQuantity;

    public Product(String identifier, String title, double price, int availableQuantity){
        this.identifier=identifier;
        this.title=title;
        this.price=price;
        this.availableQuantity = availableQuantity;
    }

    public String getIdentifier(){
        return identifier;
    }
    public void setIdentifier(String identifier){
        this.identifier=identifier;
    }


    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }


    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }


    public int getAvailableQuantity() {
        return availableQuantity;
    }
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public abstract String getDescription();
}