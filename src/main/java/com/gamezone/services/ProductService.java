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
}