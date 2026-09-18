package com.gamezone.services;

import com.gamezone.persistence.AccessoryRepository;

public class AccessoryService {

    private final AccessoryRepository accessoryRepository;

    public AccessoryService(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
    }


}
