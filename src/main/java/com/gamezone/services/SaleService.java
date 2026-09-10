package com.gamezone.services;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
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

        for (Product soldProduct : soldProducts) {
            Product storedProduct = findProductById(storedProducts, soldProduct.getIdentifier());
            if (storedProduct.getAvailableQuantity() < 1) {
                throw new IllegalStateException("Insufficient stock for product: " + storedProduct.getTitle());
            }
        }


        for (Product soldProduct : soldProducts) {
            Product storedProduct = findProductById(storedProducts, soldProduct.getIdentifier());
            int newQuantity = storedProduct.getAvailableQuantity() - 1;
            productService.updateStock(storedProduct, newQuantity);
        }

        sale.getCustomer().addToPurchaseHistory(sale);
        saleRepository.save(sale);
    }

    public List<Sale> listSales() {
        return saleRepository.load();
    }

    public List<Sale> getCustomerPurchaseHistory(Customer customer) {
        List<Sale> history = new ArrayList<>();
        for (Sale sale : saleRepository.load()) {
            if (sale.getCustomer().getIdentification().equals(customer.getIdentification())) {
                history.add(sale);
            }
        }
        return history;
    }




}