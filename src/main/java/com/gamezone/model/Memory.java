package com.gamezone.model;

import java.util.List;
/*
* Represents a memory accessory compatible with specific consoles.
 */
public class Memory extends Accessory{
    private int capacity;
    private String memoryType;

    /*
     * Creates a new memory accessory.
     *
     * @param identifier unique memory identifier
     * @param title memory title
     * @param price memory price
     * @param availableQuantity available quantity in inventory
     * @param compatibleConsoles consoles compatible with the memory
     * @param capacity capacity of the memory in GB
     * @param memoryType type of memory (e.g., DDR4, DDR5)
     */
    public Memory(String identifier, String title, double price, int availableQuantity, List<Console> compatibleConsoles, int capacity, String memoryType) {
        super(identifier, title, price, availableQuantity, compatibleConsoles);
        this.capacity = capacity;
        this.memoryType = memoryType;
    }

    /*
     * Returns the capacity of the memory in GB.
     *
     * @return the capacity of the memory
     */
    public int getCapacity() {
        return capacity;
    }
    
    /*
    *Updates the capacity of the memory.
    @param capacity the new capacity of the memory
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /*
     * Returns the type of the memory.
     *
     * @return the type of the memory
     */
    public String getMemoryType() {
        return memoryType;
    }

    /*
     * Updates the type of the memory.
     *
     * @param memoryType the new type of the memory
     */
    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
    }

    /*
     * Returns a description of the memory accessory.
     *
     * @return memory description
     */
    @Override
    public String getDescription() {
        return getTitle() + " - Capacity: " + capacity + "GB, Type: " + memoryType;
    }
}
