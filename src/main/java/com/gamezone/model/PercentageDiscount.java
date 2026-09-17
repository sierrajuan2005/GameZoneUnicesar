package com.gamezone.model;

import java.time.LocalDate;
/**
 * Represents a promotion that applies a percentage discount
 * to the total amount of a sale.
 */
public class PercentageDiscount extends Promotion {
    private double discountPercentage;


    /*
        Creates a new percentage discount promotion.    
    @param identifier unique promotion identifier
    @param name promotion name
    @param startDate promotion start date
    @param endDate promotion end date
    @param discountPercentage percentage of discount to apply
     */
    public PercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage) {
        super(identifier, name, startDate, endDate);
        this.discountPercentage = discountPercentage;
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
     * Calculates the discount for the given sale.
     *
     * @param sale sale to which the discount is applied
     * @return discount amount
     */
    @Override 
    public double calculateDiscount(Sale sale) {
        return sale.calculateTotal() * (discountPercentage / 100);
    }
}
