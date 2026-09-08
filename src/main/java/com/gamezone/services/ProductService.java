package com.gamezone.services;
import com.gamezone.domain.Product;
import com.gamezone.persistence.ProductRepository;

import java.util.List;

public class ProductService{
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public void registerProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> listProducts(){
        return productRepository.loadAll();
    }

    public void updateStock(Product product, int quantity) {
    List<Product> products = productRepository.loadAll();

    for (Product storedProduct : products) {
        if (storedProduct.getIdentifier().equals(product.getIdentifier())) {
            storedProduct.setAvailableQuantity(quantity);
            break;
        }
    }

    productRepository.saveAll(products);
}
}