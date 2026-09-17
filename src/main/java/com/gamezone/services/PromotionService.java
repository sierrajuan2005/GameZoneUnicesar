package com.gamezone.services;

import com.gamezone.model.Promotion;
import com.gamezone.persistence.PromotionRepository;

public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Promotion findByIdentifier(String identifier) {
        if (identifier == null) return null;
        for (Promotion p : promotionRepository.loadAll()) {
            if (identifier.equals(p.getIdentifier())) {
                return p;
            }
        }
        return null;
    }

    private void validateUniqueId(String identifier) {
        if (findByIdentifier(identifier) != null) {
            throw new IllegalArgumentException("A promotion with identifier " + identifier + " already exists.");
        }
    }
}
