package com.gamezone.services;

import com.gamezone.model.Warranty;
import com.gamezone.persistence.WarrantyRepository;

import java.util.List;

public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public void registerWarranty(Warranty warranty){
        if (warranty == null){
            throw new IllegalArgumentException("Warranty cannot be null");
        }
        warrantyRepository.addWarranty(warranty);
    }

    public List<Warranty> listWarranties(){
        return warrantyRepository.getAllWarranties();
    }

    public Warranty findWarrantyByIdentifier(String identifier){
        if (identifier == null || identifier.isEmpty()){
            throw new IllegalArgumentException("Identifier cannot be null or empty.");
        }
        return warrantyRepository.findByIdentifier(identifier);
    }

    public List<Warranty> reloadWarranties(){
        return warrantyRepository.loadAll();
    }

}
