package com.gamezone.model;

import java.util.List;

/*
 * Represents the abstract base class for all accessories available in GameZone.
 */
public abstract class Accessory  extends Product {
    private List<Console> compatibleConsoles;
    

    public Accessory(String identifier, String title, double price, int availability, List<Console> compatibleConsoles) {
        super(identifier, title, price, availability);
        this.compatibleConsoles = compatibleConsoles;
    }
}
