package com.gamezone.services;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Service responsible for managing product return business logic, eligibility checks,
 * inventory restoration, and monthly financial balance calculations.
 */
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;

    /*
     * Constructs a {@code ReturnService} with the required dependencies.
     *
     * @param returnRepository repository used to persist and load return records
     * @param saleService       service used to retrieve and validate sale data
     * @param productService   service used to manage product information and inventory stock
     */
    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
    }

    /*
     * Registers a new product return for a given sale, validates eligibility policies,
     * restores stock for returned products, and persists the return record.
     *
     * @param identifier identifier of the original sale
     * @param productIds list of product identifiers to return
     * @param reason     reason or cause for the return
     * @return the newly created and stored {@link Return} instance
     * @throws IllegalArgumentException if the sale does not exist, exceeds the 30-day return limit,
     *                                  or if any product does not belong to the specified sale
     */
    public Return registerReturn(String identifier, List<String> productIds, String reason) {
        Sale sale = saleService.findSaleById(identifier);
        if (sale == null) {
            throw new IllegalArgumentException("The specified sale does not exist.");
        }
        if (!sale.canBeReturned()) {
            throw new IllegalArgumentException("The return exceeds the 30-day limit.");
        }

        List<Product> productsToReturn = new ArrayList<>();
        for (String productId : productIds) {
            Product product = productService.findByIdentifier(productId);
            if (product == null || sale.getProducts().stream()
                    .noneMatch(p -> p.getIdentifier().equals(productId))) {
                throw new IllegalArgumentException("The product does not belong to the specified sale.");
            }
            productsToReturn.add(product);
            productService.restoreStock(productId, 1);
        }

        Return r = new Return(identifier + "-RET", LocalDate.now(), sale, productsToReturn, reason);
        List<Return> allReturns = returnRepository.loadAll();
        allReturns.add(r);
        returnRepository.saveAll(allReturns);
        return r;
    }

    /*
     * Retrieves all registered returns from storage.
     *
     * @return a list containing all {@link Return} records
     */
    public List<Return> viewAllReturns() {
        return returnRepository.loadAll();
    }

    /*
     * Retrieves all returns performed by a specific customer based on their identification number.
     *
     * @param customerId identification string of the customer
     * @return a list of {@link Return} records matching the customer identification
     */
    public List<Return> viewReturnsByCustomer(String customerId) {
        List<Return> result = new ArrayList<>();
        for (Return returnRecord : returnRepository.loadAll()) {
            Sale sale = returnRecord.getOriginalSale();
            if (sale != null && sale.getCustomer() != null
                    && sale.getCustomer().getIdentification().equals(customerId)) {
                result.add(returnRecord);
            }
        }
        return result;
    }

    /*
     * Retrieves all returns associated with a specific original sale.
     *
     * @param saleId identifier of the sale
     * @return a list of {@link Return} records linked to the specified sale identifier
     */
    public List<Return> viewReturnsBySale(String saleId) {
        List<Return> result = new ArrayList<>();
        for (Return returnRecord : returnRepository.loadAll()) {
            Sale sale = returnRecord.getOriginalSale();
            if (sale != null && sale.getIdentifier().equals(saleId)) {
                result.add(returnRecord);
            }
        }
        return result;
    }

    /*
     * Calculates the net financial balance for a specific month and year by subtracting
     * total refund amounts from total gross sales.
     *
     * @param month target month to analyze (1 to 12)
     * @param year  target year to analyze (must be greater than 0)
     * @return total sales amount minus total refund amount for the given period
     * @throws IllegalArgumentException if month is not between 1 and 12, or year is less than 1
     */
    public double generateMonthlyBalance(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if (year < 1) {
            throw new IllegalArgumentException("Year must be greater than zero.");
        }

        double totalSales = 0.0;
        double totalReturns = 0.0;

        for (Sale sale : saleService.listSales()) {
            if (sale.getDate().getMonthValue() == month && sale.getDate().getYear() == year) {
                totalSales += sale.calculateTotal();
            }
        }

        for (Return returnRecord : returnRepository.loadAll()) {
            if (returnRecord.getReturnDate().getMonthValue() == month && returnRecord.getReturnDate().getYear() == year) {
                totalReturns += returnRecord.getRefundAmount();
            }
        }

        return totalSales - totalReturns;
    }

}
