package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a product return associated with a sale.
 * Stores details such as identifier, date, original sale,
 * returned products, reason, and refund amount.
 */
public class Return {
    private String identifier;
    private LocalDate returnDate;
    private Sale originalSale;
    private List<Product> returnedProducts;
    private String reason;
    private double refundAmount;

    /**
     * Constructs a Return record with refund amount calculation.
     *
     * @param identifier unique identifier for the return
     * @param returnDate date of the return
     * @param originalSale sale associated with the return
     * @param returnedProducts list of products returned
     * @param reason reason for the return
     * @param refundAmount initial refund amount (will be recalculated)
     */
    public Return(String identifier, LocalDate returnDate, Sale originalSale, List<Product> returnedProducts, String reason, double refundAmount) {
        this.identifier = identifier;
        this.returnDate = returnDate;
        this.originalSale = originalSale;
        this.returnedProducts = returnedProducts;
        this.reason = reason;
        this.refundAmount = 0.0;
        calculateRefundAmount();
    }

    /**
     * Returns the identifier of the return.
     *
     * @return return identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Returns the date of the return.
     *
     * @return return date
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Returns the original sale associated with the return.
     *
     * @return original sale
     */
    public Sale getOriginalSale() {
        return originalSale;
    }

    /**
     * Returns the list of products returned.
     *
     * @return list of returned products
     */
    public List<Product> getReturnedProducts() {
        return returnedProducts;
    }

    /**
     * Returns the reason for the return.
     *
     * @return reason text
     */
    public String getReason() {
        return reason;
    }

    /**
     * Returns the refund amount for the return.
     *
     * @return refund amount
     */
    public double getRefundAmount() {
        return refundAmount;
    }

    /**
     * Calculates the refund amount based on returned products.
     *
     * @return calculated refund amount
     */
    public double calculateRefundAmount() {
        double total = 0.0;
        for (Product product : returnedProducts) {
            total += product.getPrice();
        }
        this.refundAmount = total;
        return refundAmount;
    }

    /**
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
                receipt.append("- ").append(product.getTitle())
                        .append(": $").append(product.getPrice()).append("\n");
            }
        }
        receipt.append("Reason for Return: ").append(reason).append("\n");
        receipt.append("Total Refund Amount: $").append(refundAmount).append("\n");

        return receipt.toString();
    }
}