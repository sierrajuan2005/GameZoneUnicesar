package com.gamezone.model;

import java.time.LocalDate;
/*
 * Represents a promotion that applies a percentage discount
 * to products belonging to a specific category.
 */
public class CategoryDiscount extends Promotion {
    private double discountPercentage;
    private String targetCategory;

    /*Creates a new category discount promotion.
    @param identifier unique promotion identifier
    @param name promotion name
    @param startDate promotion start date
    @param endDate promotion end date
    @param discountPercentage percentage of discount to apply
    @param targetCategory category to which the discount applies
    */

    public CategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory) {
        super(identifier, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
        this.targetCategory = targetCategory;
    }

    /*
     * Returns the discount percentage.
     *
     * @return discount percentage
     */
    public double getDiscountPercentage() {
        return discountPercentage;
    }
    
/*
     * Updates the discount percentage.
     *
     * @param discountPercentage new discount percentage
     */
    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    /*
     * Returns the target category.
     *
     * @return target category
     */
    public String getTargetCategory() {
        return targetCategory;
    }

    /*
     * Updates the target category.
     *
     * @param targetCategory new target category
     */
    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    /*
    Calculates the discount for the given sale based on the target category
    @param sale sale to which the discount is applied
    @return discount amount
    */

    @Override 
    public double calculateDiscount(Sale sale){
        double categoryTotal=0.0;
        for(Product product : sale.getProducts()){
            if(targetCategory.equals("VIDEOGAME") && product instanceof VideoGame){
                categoryTotal += product.getPrice();
            } else if(targetCategory.equals("CONSOLE") && product instanceof Console){
                categoryTotal += product.getPrice();
            }
            
        }
        return categoryTotal * (discountPercentage / 100);
    }
}
