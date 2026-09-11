package com.gamezone.model;
/*
Represents the abstract base class for all products 
available in GameZone.
This class defines the common attributes and behavior shared by different
types of products, such as video games and consoles. 
*/

public abstract class Product{
    private String identifier;
    private String title;
    private double price;
    private int availableQuantity;

    /*
    Creates a new product with the specified information.

    @param identifier unique identifier of the product
    @param title title or name of the product
    @param price price of the product
    @param availableQuantity quantity currently available in inventory
     */

    public Product(String identifier, String title, double price, int availableQuantity){
        this.identifier=identifier;
        this.title=title;
        this.price=price;
        this.availableQuantity = availableQuantity;
    }


    /*@return product identifier */
    public String getIdentifier(){
        return identifier;
    }

    /* @param identifier new identifier */
    public void setIdentifier(String identifier){
        this.identifier=identifier;
    }

    /* @return product title */
    public String getTitle(){
        return title;
    }
    
    /* @param title new title */
    public void setTitle(String title){
        this.title=title;
    }


    /* @return product price */
    public double getPrice(){
        return price;
    }
    /* @param price new price */
    public void setPrice(double price){
        this.price=price;
    }

    /* @return available quantity */
    public int getAvailableQuantity() {
        return availableQuantity;
    }
    /* @param availableQuantity new quantity */
    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
    /*
    Gets the product description.
    @return product description
     */

    public abstract String getDescription();
}