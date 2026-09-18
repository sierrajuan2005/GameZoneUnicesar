package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a sale made by a customer and handled by a seller.
 */
public class Sale {

    private String identifier;
    private LocalDate date;
    private Customer customer;
    private Seller seller;
    private List<Product> products;


    private String appliedPromotionName;
    private double discountAmount;

    /**
     * Creates a new sale.
     *
     * @param date sale date
     * @param customer customer who made the purchase
     * @param seller seller who handled the sale
     * @param products products included in the sale
     */

    public Sale(String identifier, LocalDate date, Customer customer, Seller seller, List<Product> products) {
        this.identifier = identifier;
        this.date = date;
        this.customer = customer;
        this.seller = seller;
        this.products = products;
        this.appliedPromotionName = appliedPromotionName;
        this.discountAmount = discountAmount;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


    public String getAppliedPromotionName() {
        return appliedPromotionName;
    }

    public void setAppliedPromotionName(String appliedPromotionName) {
        this.appliedPromotionName = appliedPromotionName;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Adds a product to the sale.
     *
     * @param product product to add
     */
    public void addProduct(Product product) {
        this.products.add(product);
    }

    /**
     * Calculates the total price of the sale.
     *
     * @return total price
     */
    public double calculateTotal() {
        double total = 0.0;

        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }
    /**
     * Builds a formatted, human-readable receipt in Spanish, showing
     * the subtotal, the applied promotion's discount (if any), and
     * the final total.
     *
     * @return the formatted receipt text
     */
    public String generateReceipt() {
        double subtotal = calculateTotal();
        double finalTotal = subtotal - discountAmount;

        StringBuilder receipt = new StringBuilder();
        receipt.append("Recibo de venta\n");
        receipt.append("Identificador: ").append(identifier).append("\n");
        receipt.append("Subtotal: $").append(subtotal).append("\n");

        if (appliedPromotionName != null && discountAmount > 0) {
            receipt.append("Descuento aplicado (").append(appliedPromotionName).append("): -$")
                    .append(discountAmount).append("\n");
        } else {
            receipt.append("Descuento aplicado: ninguno\n");
        }

        receipt.append("Total final: $").append(finalTotal);
        return receipt.toString();
    }


    public boolean canBeReturned(){
        LocalDate currentDate = LocalDate.now();
        LocalDate returnDeadline = date.plusDays(30);

        return !currentDate.isBefore(date)
                && !currentDate.isAfter(returnDeadline);
    }

    /*
     * Returns a string representation of the sale.
     *
     * @return a formatted string containing the sale details
     */
    @Override
    public String toString() {
        return "Sale{" +
                "identifier='" + identifier + '\'' +
                ", date=" + date +
                ", customer=" + customer +
                ", seller=" + seller +
                ", products=" + products +
                '}';
    }
}

