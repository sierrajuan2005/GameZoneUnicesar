package com.gamezone.model;

import java.util.List;
/*
*Represents a controller accessoty compatible with specific consoles.
 */
public class Controller extends Accessory{
    private String connectionType;

    /*
     * Creates a new controller.
     *
     * @param identifier unique controller identifier
     * @param title controller title
     * @param price controller price
     * @param availableQuantity available quantity in inventory
     * @param compatibleConsoles consoles compatible with the controller
     * @param connectionType type of connection for the controller
     */
    public Controller(String identifier, String title, double price, int availableQuantity, List<Console> compatibleConsoles, String connectionType) {
        super(identifier, title, price, availableQuantity, compatibleConsoles);
        this.connectionType = connectionType;
    }

    /*
     * Returns the type of connection for the controller.
     *
     * @return the connection type
     */
    public String getConnectionType() {
        return connectionType;
    }

    /*
     * Updates the type of connection for the controller.
     *
     * @param connectionType the new connection type
     */
    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    /*
     * Returns a description of the controller.
     *
     * @return controller description
     */
    @Override
    public String getDescription() {
        return getTitle() + " - Connection: " + connectionType;
    }
}
