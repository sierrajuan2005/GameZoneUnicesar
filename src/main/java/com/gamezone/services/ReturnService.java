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

}
