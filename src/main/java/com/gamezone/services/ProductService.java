package com.gamezone.services;
import com.gamezone.model.Product;
import com.gamezone.persistence.ProductRepository;

import java.util.List;

/*
Provides services for managing GameZone products.
 */
public class ProductService{
    private final ProductRepository productRepository;

     /*
    Creates a product service.
    @param productRepository repository used to manage products
     */
    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }


    /*Registers a new product.
    @param product product to register
    @throws IllegalArgumentException if the product identifier already exists
     */
    public void registerProduct(Product product) {

        List<Product> products = productRepository.loadAll();

        for (Product storedProduct : products) {
            if (storedProduct.getIdentifier().equals(product.getIdentifier())) {
                throw new IllegalArgumentException(
                    "A product with this identifier already exists."
                );
            }
        }

        products.add(product);
        productRepository.saveAll(products);
    }

    /*
    Lists all registered products.
    @return list of registered products
     */
    public List<Product> listProducts(){
        return productRepository.loadAll();
    }

    /*
     * Finds a product in the repository by its unique identifier.
     *
     * @param identifier the unique identifier of the product to search for.
     * @return the corresponding {@link Product} if found,
     *         or {@code null} if no product exists with the given identifier.
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


     /*
    Updates the stock quantity of a product
    @param product product whose stock will be updated
    @param quantity new stock quantity
    @throws IllegalArgumentException if the quantity is negative or the product does not exist
     */
    public void updateStock(Product product, int quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException(
                    "Stock quantity cannot be negative."
            );
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
            throw new IllegalArgumentException(
                    "Product not found."
            );
        }

        productRepository.saveAll(products);
    }

    /*
     * Restores the stock quantity for a specific product.
     *
     * @param productId the unique identifier of the product to update.
     * @param quantity  the amount of stock to add back; must be greater than zero.
     * @throws IllegalArgumentException if {@code quantity} is less than or equal to zero,
     *                                  or if no product is found with the given {@code productId}.
     */
    public void restoreStock(String productId, int quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity to restore must be greater than zero."
            );
        }

        Product product = findByIdentifier(productId);

        if (product == null) {
            throw new IllegalArgumentException(
                    "Product not found."
            );
        }

        int newQuantity =
                product.getAvailableQuantity() + quantity;

        updateStock(product, newQuantity);
    }
}