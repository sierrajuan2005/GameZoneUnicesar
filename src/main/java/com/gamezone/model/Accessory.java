package com.gamezone.model;

import java.util.List;

/*
 * Represents the abstract base class for all accessories available in GameZone.
 */
public abstract class Accessory  extends Product {
    private List<Console> compatibleConsoles;
    

    /*
     * Creates a new accessory.
     *
     * @param identifier unique accessory identifier
     * @param title accessory title
     * @param price accessory price
     * @param availableQuantity available quantity in inventory
     * @param compatibleConsoles consoles compatible with the accessory
     */
    public Accessory(String identifier, String title, double price, int availability, List<Console> compatibleConsoles) {
        super(identifier, title, price, availability);
        this.compatibleConsoles = compatibleConsoles;
    }

    /*
    * Returns the consoles compatible with the accessory.
    *
    * @return the list of compatible consoles
    */
    public List<Console> getCompatibleConsoles() {
        return compatibleConsoles;
    }

    /*
    *Updates the consoles compatible with the accessory.

    @param compatibleConsoles the new list of compatible consoles
    */

    public void setCompatibleConsoles(List<Console> compatibleConsoles) {
        this.compatibleConsoles = compatibleConsoles;
    }



    /*
    *Returns a description of the accessory.
    *
    * @return accessory description
    */

    @Override
    public String getDescription() {
        return getTitle() + " - Compatible consoles: " + compatibleConsoles.size();
    }

    
}
