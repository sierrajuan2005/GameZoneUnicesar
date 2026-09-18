package com.gamezone.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Customer entity extending Person.
 * Stores email and purchase history.
 */
public class Customer extends Person {

    private String email;
    private List<Sale> purchaseHistory;

    /**
     * Creates a customer with name, identification, phone and email.
     *
     * @param name           customer's full name
     * @param identification unique identifier
     * @param phone          contact phone
     * @param email          customer's email
     */
    public Customer(String name, String identification, String phone, String email) {
        super(name, identification, phone);
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    /**
     * Creates a customer with name and identification.
     *
     * @param name           customer's full name
     * @param identification unique identifier
     */
    public Customer(String name, String identification) {
        super(name, identification);
    }


    /** @return customer's email */
    public String getEmail() {
        return email;
    }

    /** Sets customer's email */
    public void setEmail(String email) {
        this.email = email;
    }

    /** @return purchase history list */
    public List<Sale> getPurchaseHistory() {
        return purchaseHistory;
    }

    /** Adds a sale to purchase history */
    public void addToPurchaseHistory(Sale sale) {
        this.purchaseHistory.add(sale);
    }

    /** @return role string "Customer" */
    @Override
    public String getRol() {
        return "Customer";
    }

    /** @return string with customer details */
    @Override
    public String toString() {
        return "Customer{name='" + getName() + "',identification='" + getIdentification() +
                "', phone='" + getPhone() + "', email='" + getEmail()
                + "', purchaseHistory=" + getPurchaseHistory() + "}";
    }
}
