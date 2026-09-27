package com.gamezone.services;

import com.gamezone.model.Accessory;
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
    private final AccessoryService accessoryService;

    public ReturnService(ReturnRepository returnRepository, SaleService saleService,
                         ProductService productService, AccessoryService accessoryService) {
        this.returnRepository = returnRepository;
        this.saleService = saleService;
        this.productService = productService;
        this.accessoryService = accessoryService;
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

            if (product instanceof Accessory accessory) {
                accessoryService.restoreStock(accessory.getIdentifier(), 1);
                sale.getProducts().removeIf(p -> p.getIdentifier().equals(productId));
            } else {
                productService.restoreStock(productId, 1);
            }
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

    public double calculateMonthlySales(int month, int year) {
        return saleService.listSales().stream()
                .filter(sale -> sale.getDate().getMonthValue() == month && sale.getDate().getYear() == year)
                .mapToDouble(Sale::calculateTotal)
                .sum();
    }

    public double calculateMonthlyReturns(int month, int year) {
        return returnRepository.loadAll().stream()
                .filter(returnRecord -> returnRecord.getReturnDate().getMonthValue() == month
                        && returnRecord.getReturnDate().getYear() == year)
                .mapToDouble(Return::getRefundAmount)
                .sum();
    }


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