package com.gamezone.services;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.List;

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

    private void savePromotion(Promotion promotion) {
        List<Promotion> promotions = promotionRepository.loadAll();
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
    }

    public void registerPercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage) {
        validateUniqueId(identifier);
        PercentageDiscount pd = new PercentageDiscount(identifier, name, startDate, endDate, discountPercentage);
        savePromotion(pd);
    }

    public void registerCategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory) {
        validateUniqueId(identifier);
        CategoryDiscount cd = new CategoryDiscount(identifier, name, startDate, endDate, discountPercentage, targetCategory);
        savePromotion(cd);
    }

    public void registerBulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minQuantity, double discountPercentage) {
        validateUniqueId(identifier);
        BulkPurchaseDiscount bd = new BulkPurchaseDiscount(identifier, name, startDate, endDate, minQuantity, discountPercentage);
        savePromotion(bd);
    }
}
