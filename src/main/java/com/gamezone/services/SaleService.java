package com.gamezone.services;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.services.ProductService;

public class SaleService {
    private SaleRepository saleRepository;
    private ProductRepository productRepository;
    private PersonService personService;


    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, PersonService personService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.personService = personService;
    }

    public void registerSale(Sale sale) {
        List<Product> soldProducts = sale.getProducts();

        if (soldProducts.isEmpty()) {
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }

        List<Product> storedProducts = productService.listProducts();


    }

}