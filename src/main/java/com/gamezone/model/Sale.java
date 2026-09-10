package com.gamezone.model;

import java.time.LocalDate;
import java.util.List;
public class Sale {

    private LocalDate date;
    private Customer customer;
    private Seller seller;
    private List<Product> products;

    public Sale(LocalDate date, Customer customer, Seller seller, List<Product> products) {
        this.date = date;
        this.customer = customer;
        this.seller = seller;
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public double calculateTotal(){
        double total = 0.0;
        for (Product product : products){
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "date=" + date +
                ", customer=" + customer +
                ", seller=" + seller +
                ", products=" + products +
                '}';
    }

}

