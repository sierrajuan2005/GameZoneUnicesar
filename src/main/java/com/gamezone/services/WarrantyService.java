package com.gamezone.services;

import com.gamezone.persistence.WarrantyRepository;

public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }
}
