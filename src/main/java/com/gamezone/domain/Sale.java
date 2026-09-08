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


}

