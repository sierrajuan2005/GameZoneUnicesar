package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

public class Return {
    private String identifier;
    private LocalDate returnDate;
    private Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    public Return(String identifier, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String reason) {
        this.identifier = identifier;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = 0.0;

        calculateRefundAmount();
    }

    public String getIdentifier() {
        return identifier;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public Sale getOriginalSale() {
        return originalSale;
    }
    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }
    public String getReason() {
        return reason;
    }
    public double getRefundAmount() {
        return refundAmount;
    }

    public double calculateRefundAmount() {
        double total = 0.0;
        for (Product product : returnedProducts) {
            total += product.getPrice();
        }
        this.refundAmount = total;
        return refundAmount;
    }

    /*
     * Generates a detailed text receipt for the return record.
     *
     * @return a formatted string containing return details and refund summary
     */
    public String generateReturnReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Return Receipt\n");
        receipt.append("Identifier: ").append(identifier).append("\n");
        receipt.append("Return Date: ").append(returnDate).append("\n");
        receipt.append("Original Sale ID: ")
                .append(originalSale != null ? originalSale.getIdentifier() : "")
                .append("\n");
        receipt.append("Returned Products:\n");
        if (returnedProducts != null) {
            for (Product product : returnedProducts) {
                receipt.append("- ").append(product.getTitle()).append(": $").append(product.getPrice()).append("\n");
            }
        }
        receipt.append("Reason for Return: ").append(reason).append("\n");
        receipt.append("Total Refund Amount: $").append(refundAmount).append("\n");

        return receipt.toString();
    }
}
