package com.gamezone.domain;

import java.time.LocalDate;
import java.util.List;

public class Sale {

    private LocalDate date;
    private Customer customer;
    private Seller seller;
    private List<product> products;

    public Sale(LocalDate date, Customer customer, Seller seller, List<product> products) {
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

    public List<product> getProducts() {
        return products;
    }

    public void setProducts(List<product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }






}

