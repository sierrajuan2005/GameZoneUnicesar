package com.gamezone.services;

import com.gamezone.model.*;
import com.gamezone.persistence.AccessoryRepository;

import java.util.List;

public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }

    public void registerController(String id, String title, double price, int availability, List<Console> consoles, String connectionType) {
        Controller controller = new Controller(id, title, price, availability, consoles, connectionType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(controller);
        accessoryRepository.saveAll(accessories);
    }

    public void registerCable(String id, String title, double price, int availability, List<Console> consoles, double length, String connectorType) {
        Cable cable = new Cable(id, title, price, availability, consoles, length, connectorType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(cable);
        accessoryRepository.saveAll(accessories);
    }

    public void registerMemory(String id, String title, double price, int availability, List<Console> consoles, int capacity, String memoryType) {
        Memory memory = new Memory(id, title, price, availability, consoles, capacity, memoryType);
        List<Accessory> accessories = accessoryRepository.loadAll();
        accessories.add(memory);
        accessoryRepository.saveAll(accessories);
    }


}
