package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

    private String email;
    private List<Sale> purchaseHistory;

    public Customer(String name, String identification, String phone, String email) {
        super(name, identification, phone);
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Sale> getPurchaseHistory() {
        return purchaseHistory;
    }

    public void addToPurchaseHistory(Sale sale) {
        this.purchaseHistory.add(sale);
    }

    @Override
    public String getRol() {
        return "Customer";
    }
}
