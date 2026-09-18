package com.gamezone.services;

import com.gamezone.model.*;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;

/*
 * Service layer for managing accessories in the system.
 * Provides methods to register, query, and update accessory data,
 * delegating persistence operations to {@link AccessoryRepository}.
 */
public class AccessoryService {

    /* Repository for accessory persistence. */
    private final AccessoryRepository accessoryRepository;

    /*
     * Constructs the service with the given repository.
     *
     * @param accessoryRepository repository instance for persistence
     */
    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    /*
     * Registers a new controller accessory.
     *
     * @param id unique identifier
     * @param title name of the controller
     * @param price price of the controller
     * @param availability available stock
     * @param consoles compatible consoles
     * @param connectionType type of connection (e.g. wireless, USB)
     */
    public void registerController(String id, String title, double price, int availability,
                                   List<Console> consoles, String connectionType) {
        Controller controller = new Controller(id, title, price, availability, consoles, connectionType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(controller);
        accessoryRepository.saveAll(accessories);
    }

    /*
     * Registers a new cable accessory.
     *
     * @param id unique identifier
     * @param title name of the cable
     * @param price price of the cable
     * @param availability available stock
     * @param consoles compatible consoles
     * @param length cable length in meters
     * @param connectorType connector type (e.g. HDMI, USB-C)
     */
    public void registerCable(String id, String title, double price, int availability,
                              List<Console> consoles, double length, String connectorType) {
        Cable cable = new Cable(id, title, price, availability, consoles, length, connectorType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(cable);
        accessoryRepository.saveAll(accessories);
    }

    /*
     * Registers a new memory accessory.
     *
     * @param id unique identifier
     * @param title name of the memory
     * @param price price of the memory
     * @param availability available stock
     * @param consoles compatible consoles
     * @param capacity memory capacity in GB
     * @param memoryType type of memory (e.g. SSD, HDD)
     */
    public void registerMemory(String id, String title, double price, int availability,
                               List<Console> consoles, int capacity, String memoryType) {
        Memory memory = new Memory(id, title, price, availability, consoles, capacity, memoryType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(memory);
        accessoryRepository.saveAll(accessories);
    }

    /*
     * Lists all accessories.
     *
     * @return list of all accessories
     */
    public List<Accessory> listAllAccessories() {
        return accessoryRepository.loadAll();
    }

    /*
     * Lists accessories filtered by type.
     *
     * @param type accessory type (Controller, Cable, Memory)
     * @return list of accessories of the given type
     */
    public List<Accessory> listAccessoriesByType(String type) {
        return accessoryRepository.loadAll().stream()
                .filter(a -> a.getClass().getSimpleName().equalsIgnoreCase(type))
                .toList();
    }

    /*
     * Finds accessories compatible with a given console.
     *
     * @param consoleId identifier of the console
     * @return list of compatible accessories
     */
    public List<Accessory> findAccessoriesCompatibleWith(String consoleId) {
        return accessoryRepository.loadAll().stream()
                .filter(a -> a.getCompatibleConsoles().stream()
                        .anyMatch(c -> c.getIdentifier().equals(consoleId)))
                .toList();
    }

    /*
     * Finds an accessory by its identifier.
     *
     * @param id accessory identifier
     * @return accessory if found, otherwise null
     */
    public Accessory findById(String id) {
        return accessoryRepository.loadAll().stream()
                .filter(a -> a.getIdentifier().equals(id))
                .findFirst()
                .orElse(null);
    }

    /*
     * Updates the stock quantity of an accessory.
     *
     * @param accessoryId accessory identifier
     * @param quantity new available quantity
     */
    public void updateStock(String accessoryId, int quantity) {
        List<Accessory> accessories = accessoryRepository.loadAll();
        for (Accessory a : accessories) {
            if (a.getIdentifier().equals(accessoryId)) {
                a.setAvailableQuantity(quantity);
                break;
            }
        }
        accessoryRepository.saveAll(accessories);
    }
}