package com.gamezone.services;

import com.gamezone.model.Product;
import com.gamezone.model.Return;
import com.gamezone.model.Sale;
import com.gamezone.persistence.ReturnRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReturnService {

    private final ReturnRepository returnRepository;
    private final SaleService saleService;
    private final ProductService productService;

    public ReturnService(ReturnRepository returnRepository, SaleService saleService, ProductService productService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
    }

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

    public List<Return> viewAllReturns() {
        return returnRepository.loadAll();
    }

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
