package com.gamezone.services;

import com.gamezone.model.*;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        BasicWarranty warranty = new BasicWarranty(product.getIdentifier(), product, sale, startDate);
        warrantyRepository.addWarranty(warranty);
        return warranty;
    }

    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        ExtendedWarranty warranty = new ExtendedWarranty(product.getIdentifier(), product, sale, startDate);
        warrantyRepository.addWarranty(warranty);
        return warranty;
    }

    public Warranty findWarrantyByProduct(String productIdentifier, String saleIdentifier) {
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> w.getProduct().getIdentifier().equals(productIdentifier)
                        && w.getSale().getIdentifier().equals(saleIdentifier))
                .findFirst()
                .orElse(null);
    }

    public List<Warranty> listActiveWarranties() {
        LocalDate today = LocalDate.now();
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> w.isActive(today))
                .collect(Collectors.toList());
    }
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> !w.getEndDate().isBefore(today) && !w.getEndDate().isAfter(limit))
                .collect(Collectors.toList());
    }


}
