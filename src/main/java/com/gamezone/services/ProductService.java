package com.gamezone.services;
import com.gamezone.domain.Product;
import com.gamezone.persistence.ProductRepository;

import java.util.List;

public class ProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

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

    public List<Product> listProducts(){
        return productRepository.loadAll();
    }

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