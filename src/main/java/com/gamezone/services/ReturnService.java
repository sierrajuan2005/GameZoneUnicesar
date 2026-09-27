package com.gamezone.services;

import com.gamezone.model.Accessory;
import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for managing product and accessory returns.
 * Handles eligibility checks, inventory restoration, sale updates,
 * warranty cancellation, and monthly financial balance calculations.
 */
public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;
    private final AccessoryService accessoryService;
    private final WarrantyService warrantyService;

    /**
     * Constructs a ReturnService with required dependencies.
     *
     * @param returnRepository repository for persisting returns
     * @param saleService service for managing sales
     * @param productService service for managing products
     * @param accessoryService service for managing accessories
     * @param warrantyService service for managing warranties
     */
    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService, AccessoryService accessoryService, WarrantyService warrantyService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
        this.warrantyService = warrantyService;
    }

    /**
     * Registers a return for a given sale and products.
     * Restores stock depending on product type, cancels warranties,
     * calculates refund amount including warranty reimbursement,
     * and persists the return.
     *
     * @param identifier sale identifier
     * @param productIds list of product identifiers to return
     * @param reason reason for the return
     * @return created Return record with refund amount
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
        double refundAmount = 0.0;

        for (String productId : productIds) {
            Product product = productService.findByIdentifier(productId);
            if (product == null || sale.getProducts().stream()
                    .noneMatch(p -> p.getIdentifier().equals(productId))) {
                throw new IllegalArgumentException("The product does not belong to the specified sale.");
            }

            productsToReturn.add(product);

            if (product instanceof Accessory accessory) {
                accessoryService.restoreStock(accessory.getIdentifier(), 1);
                sale.getProducts().removeIf(p -> p.getIdentifier().equals(productId));
            } else {
                productService.restoreStock(productId, 1);
            }

            double warrantyRefund = warrantyService.cancelWarranties(productId, identifier);
            refundAmount += warrantyRefund;
            refundAmount += product.getPrice();
        }

        Return r = new Return(identifier + "-RET", LocalDate.now(), sale, productsToReturn, reason, refundAmount);
        List<Return> allReturns = returnRepository.loadAll();
        allReturns.add(r);
        returnRepository.saveAll(allReturns);

        return r;
    }

    /**
     * Retrieves all registered returns.
     *
     * @return list of all returns
     */
    public List<Return> viewAllReturns() {
        return returnRepository.loadAll();
    }

    /**
     * Retrieves returns filtered by customer identifier.
     *
     * @param customerId identifier of the customer
     * @return list of returns belonging to the customer
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

    /**
     * Retrieves returns filtered by sale identifier.
     *
     * @param saleId identifier of the sale
     * @return list of returns belonging to the sale
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

    /**
     * Calculates the total sales for a given month and year.
     *
     * @param month month number (1-12)
     * @param year year number
     * @return total sales amount
     */
    public double calculateMonthlySales(int month, int year) {
        return saleService.listSales().stream()
                .filter(sale -> sale.getDate().getMonthValue() == month && sale.getDate().getYear() == year)
                .mapToDouble(Sale::calculateTotal)
                .sum();
    }

    /**
     * Calculates the total returns for a given month and year.
     *
     * @param month month number (1-12)
     * @param year year number
     * @return total returns amount
     */
    public double calculateMonthlyReturns(int month, int year) {
        return returnRepository.loadAll().stream()
                .filter(returnRecord -> returnRecord.getReturnDate().getMonthValue() == month
                        && returnRecord.getReturnDate().getYear() == year)
                .mapToDouble(Return::getRefundAmount)
                .sum();
    }

    /**
     * Generates monthly balance by subtracting returns from sales.
     *
     * @param month month number (1-12)
     * @param year year number
     * @return net balance
     */
    public double generateMonthlyBalance(int month, int year) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if (year < 1) {
            throw new IllegalArgumentException("Year must be greater than zero.");
        }
        return calculateMonthlySales(month, year) - calculateMonthlyReturns(month, year);
    }
}