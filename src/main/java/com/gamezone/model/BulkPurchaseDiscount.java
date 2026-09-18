package com.gamezone.model;

import java.time.LocalDate;

/*
 * Represents a promotion that applies a percentage discount
 * when a sale contains a minimum number of products.
 */
public class BulkPurchaseDiscount extends Promotion{
    
    private int minimumQuantity;
    private double discountPercentage;

    /*
    * Creates a new bulk purchase discount promotion.
     *
    * @param identifier unique promotion identifier
     * @param name promotion name
    * @param startDate promotion start date
    * @param endDate promotion end date
    * @param minimumQuantity minimum number of products required
    * @param discountPercentage percentage of discount to apply
    */

    public BulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minimumQuantity, double discountPercentage) {
        super(identifier, name, startDate, endDate);
        this.minimumQuantity = minimumQuantity;
        this.discountPercentage = discountPercentage;
    }

    /*
     * Returns the minimum quantity required for the discount.
     *
     * @return minimum quantity
     */
    public int getMinimumQuantity() {
        return minimumQuantity;
    }

    /*
     * Updates the minimum quantity required for the discount.
     *
     * @param minimumQuantity new minimum quantity
     */
    public void setMinimumQuantity(int minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
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
        if (sale.getProducts().size() >= minimumQuantity) {
            return sale.calculateTotal() * (discountPercentage / 100);
        }
        return 0;
    }
}
