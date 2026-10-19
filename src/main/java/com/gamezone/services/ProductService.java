package com.gamezone.services;

import com.gamezone.model.Product;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.validation.ProductValidator;

import java.util.List;

/**
 * Provides services for managing GameZone products.
 * Delegates persistence to ProductRepository and validation to ProductValidator.
 */
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Creates a product service.
     *
     * @param productRepository repository used to manage products
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Registers a new product.
     *
     * @param product product to register
     * @throws InvalidDataException if product data is invalid
     * @throws BusinessRuleException if a product with the same identifier already exists
     */
    public void registerProduct(Product product) {
        List<Product> products = productRepository.loadAll();

        ProductValidator.validateProductData(
                product.getIdentifier(),
                product.getTitle(),
                product.getPrice(),
                product.getAvailableQuantity()
        );

        for (Product storedProduct : products) {
            if (storedProduct.getIdentifier().equals(product.getIdentifier())) {
                throw new BusinessRuleException("A product with this identifier already exists.");
            }
        }

        products.add(product);
        productRepository.saveAll(products);
    }

    /**
     * Lists all registered products.
     *
     * @return list of registered products
     */
    public List<Product> listProducts() {
        return productRepository.loadAll();
    }

    /**
     * Finds a product in the repository by its unique identifier.
     *
     * @param identifier the unique identifier of the product to search for
     * @return the corresponding Product if found, or null if not found
     */
    public Product findByIdentifier(String identifier) {
        List<Product> products = productRepository.loadAll();
        for (Product product : products) {
            if (product.getIdentifier().equals(identifier)) {
                return product;
            }
        }
        return null;
    }

    /**
     * Updates the stock quantity of a product.
     *
     * @param product product whose stock will be updated
     * @param quantity new stock quantity
     * @throws InvalidDataException if quantity is negative
     * @throws ResourceNotFoundException if product does not exist
     */
    public void updateStock(Product product, int quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("quantity", "Stock quantity cannot be negative.");
        }

        List<Product> products = productRepository.loadAll();
        boolean found = false;

        for (Product storedProduct : products) {
            if (storedProduct.getIdentifier().equals(product.getIdentifier())) {
                storedProduct.setAvailableQuantity(quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new ResourceNotFoundException("Product", product.getIdentifier());
        }

        productRepository.saveAll(products);
    }

    /**
     * Restores the stock quantity for a specific product.
     *
     * @param productId the unique identifier of the product to update
     * @param quantity the amount of stock to add back; must be greater than zero
     * @throws InvalidDataException if quantity is less than or equal to zero
     * @throws ResourceNotFoundException if product is not found
     */
    public void restoreStock(String productId, int quantity) {
        if (quantity <= 0) {
            throw new InvalidDataException("quantity", "Quantity to restore must be greater than zero.");
        }

        Product product = findByIdentifier(productId);

        if (product == null) {
            throw new ResourceNotFoundException("Product", productId);
        }

        int newQuantity = product.getAvailableQuantity() + quantity;
        updateStock(product, newQuantity);
    }
}