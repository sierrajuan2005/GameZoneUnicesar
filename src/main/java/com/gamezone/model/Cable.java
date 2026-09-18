package com.gamezone.model;

import java.util.List;

/*
 * Represents a cable accessory compatible with specific consoles.
 */
public class Cable extends Accessory {
    private double length;
    private String connectorType;

    /*
     * Creates a new cable.
     *
     * @param identifier unique cable identifier
     * @param title cable title
     * @param price cable price
     * @param availableQuantity available quantity in inventory
     * @param compatibleConsoles consoles compatible with the cable
     * @param length length of the cable in meters
     * @param connectorType type of connector for the cable
     */

    public Cable(String identifier, String title, double price, int availableQuantity, List<Console> compatibleConsoles, double length, String connectorType) {
        super(identifier, title, price, availableQuantity, compatibleConsoles);
        this.length = length;
        this.connectorType = connectorType;
    }

    /*
     * Returns the length of the cable in meters.
     *
     * @return the length of the cable
     */
    public double getLength() {
        return length;
    }

    /*
     * Updates the length of the cable.
     *
     * @param length the new length of the cable
     */
    public void setLength(double length) {
        this.length = length;
    }

    /*
     * Returns the type of connector for the cable.
     *
     * @return the connector type
     */
    public String getConnectorType() {
        return connectorType;
    }

    /*
     * Updates the type of connector for the cable.
     *
     * @param connectorType the new connector type
     */
    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    /*
     * Returns a description of the cable.
     *
     * @return cable description
     */
    @Override
    public String getDescription() {
        return getTitle() + " - Length: " + length + "m, Connector: " + connectorType;
    }
}
