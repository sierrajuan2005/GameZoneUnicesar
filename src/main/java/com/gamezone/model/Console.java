package com.gamezone.model;
/*
Represents a console product.
 */

public class Console extends Product{
    private String brand;
    private String model;
    private String generation;

    /*
    Creates a console product.
    @param identifier product identifier
    @param title product title
    @param price product price
    @param availableQuantity available quantity
    @param brand console brand
    @param model console model
    @param generation console generation
     */

    public Console(String identifier, String title, double price, int availableQuantity, String brand, String model, String generation) {
        super(identifier, title, price, availableQuantity);
        this.brand = brand;
        this.model = model;
        this.generation = generation;
    }

    /*@return console brand */
    public String getBrand(){
        return brand;
    }
    /*@param brand new brand */
    public void setBrand(String brand){
        this.brand=brand;
    }

    /*@return console model */
    public String getModel(){
        return model;
    }
    /*@param model new model */
    public void setModel(String model){
        this.model=model;
    } 

    /*@return console generation */
    public String getGeneration(){
        return generation;
    }
    /*@param generation new generation */
    public void setGeneration(String generation){
        this.generation=generation;
    }
     /*
     Gets the console description.
     @return console description
     */
    @Override 
    public String getDescription(){
        return getTitle() + " - " + brand + " - Modelo: " + model + " - Generación: "+ generation;
    }
}