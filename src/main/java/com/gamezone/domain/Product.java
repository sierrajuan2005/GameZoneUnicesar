package com.gamezone.domain;
public abstract class Product{
    private String identifier;
    private String title;
    private double price;
    private int availableQuantify;

    public Product(String identifier, String title, double price, int availableQuantify){
        this.identifier=identifier;
        this.title=title;
        this.price=price;
        this.availableQuantify=availableQuantify;
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


    public int getavailableQuantify(){
        return availableQuantify;
    }
    public void setAvailableQuantify(int availableQuantify){
        this.availableQuantify=availableQuantify;
    }

    public abstract String getDescription();
}