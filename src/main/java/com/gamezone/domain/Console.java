package com.gamezone.domain;
public class Console extends Product{
    private String brand;
    private String model;
    private String generation;


    public String getBrand(){
        return brand;
    }
    public void setBrand(String brand){
        this.brand=brand;
    }


    public String getmodel(){
        return model;
    }
    public void setModel(String model){
        this.model=model;
    } 


    public String getGeneration(){
        return generation;
    }
    public void setGeneration(String generation){
        this.generation=generation;
    }

    @Override 
    public String getDescription(){
        return getTitle() + " - " + brand + " - Modelo: " + model + " - Generación: "+ generation;
    }
}