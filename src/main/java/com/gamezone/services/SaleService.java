package com.gamezone.services;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.persistence.SaleRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the business logic related to sales.
 */
public class SaleService {

    private SaleRepository saleRepository;
    private ProductService productService;

    /**
     * Creates a SaleService with the required repositories and services.
     *
     * @param saleRepository repository used to store sales
     * @param productService service used to manage products and stock
     */
    public SaleService(SaleRepository saleRepository, ProductService productService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
    }

    /**
     * Registers a sale and updates the stock of the sold products.
     *
     * @param sale sale to register
     */
    public void registerSale(Sale sale) {
        List<Product> soldProducts = sale.getProducts();

        if (soldProducts.isEmpty()) {
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }

        List<Product> storedProducts = productService.listProducts();

        for (Product soldProduct : soldProducts) {
            Product storedProduct = findProductById(storedProducts, soldProduct.getIdentifier());

            if (storedProduct.getAvailableQuantity() < 1) {
                throw new IllegalStateException(
                        "Insufficient stock for product: " + storedProduct.getTitle()
                );
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

    /**
     * Returns all registered sales.
     *
     * @return list of registered sales
     */
    public List<Sale> listSales() {
        return saleRepository.load();
    }

    /**
     * Returns the sales made by a specific customer.
     *
     * @param customer customer whose purchase history is requested
     * @return list of sales made by the customer
     */
    public List<Sale> getCustomerPurchaseHistory(Customer customer) {
        List<Sale> history = new ArrayList<>();

        for (Sale sale : saleRepository.load()) {
            if (sale.getCustomer().getIdentification().equals(customer.getIdentification())) {
                history.add(sale);
            }
        }

        return history;
    }

    /**
     * Returns the sales handled by a specific seller.
     *
     * @param seller seller whose sales history is requested
     * @return list of sales handled by the seller
     */
    public List<Sale> getSellerSalesHistory(Seller seller) {
        List<Sale> history = new ArrayList<>();

        for (Sale sale : saleRepository.load()) {
            if (sale.getSeller().getIdentification().equals(seller.getIdentification())) {
                history.add(sale);
            }
        }

        return history;
    }

    private Product findProductById(List<Product> products, String identifier) {
        for (Product product : products) {
            if (product.getIdentifier().equals(identifier)) {
                return product;
            }
        }

        throw new IllegalArgumentException("Product not found: " + identifier);
    }
}