package com.gamezone.services;

import com.gamezone.model.*;
import com.gamezone.persistence.PromotionRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 * Service class responsible for managing promotion business logic, registration,
 * querying active promotions, and selecting the best applicable discount for a sale.
 */
public class PromotionService {

    private final PromotionRepository promotionRepository;

    /*
     * Constructs a PromotionService with the specified repository.
     *
     * @param promotionRepository the repository used for promotion persistence operations
     */
    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    /*
     * Finds a promotion by its unique identifier.
     *
     * @param identifier the unique identifier of the promotion
     * @return the {@link Promotion} if found; {@code null} otherwise
     */
    public Promotion findById(String identifier) {
        if (identifier == null) return null;
        for (Promotion p : promotionRepository.loadAll()) {
            if (identifier.equals(p.getIdentifier())) {
                return p;
            }
        }
        return null;
    }

    /*
     * Validates that the promotion identifier is unique.
     *
     * @param identifier the identifier to validate
     * @throws IllegalArgumentException if a promotion with the given identifier already exists
     */
    private void validateUniqueId(String identifier) {
        if (findById(identifier) != null) {
            throw new IllegalArgumentException("A promotion with identifier " + identifier + " already exists.");
        }
    }

    /*
     * Saves a new promotion by appending it to the existing repository list.
     *
     * @param promotion the promotion to save
     */
    private void savePromotion(Promotion promotion) {
        List<Promotion> promotions = promotionRepository.loadAll();
        promotions.add(promotion);
        promotionRepository.saveAll(promotions);
    }

    /*
     * Registers a new percentage discount promotion.
     *
     * @param identifier         the unique identifier
     * @param name               the promotion name
     * @param startDate          the start date of validity
     * @param endDate            the end date of validity
     * @param discountPercentage the percentage discount to apply
     */
    public void registerPercentageDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage) {
        validateUniqueId(identifier);
        PercentageDiscount pd = new PercentageDiscount(identifier, name, startDate, endDate, discountPercentage);
        savePromotion(pd);
    }

    /*
     * Registers a new category discount promotion.
     *
     * @param identifier         the unique identifier
     * @param name               the promotion name
     * @param startDate          the start date of validity
     * @param endDate            the end date of validity
     * @param discountPercentage the percentage discount to apply
     * @param targetCategory     the specific category eligible for discount
     */
    public void registerCategoryDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, double discountPercentage, String targetCategory) {
        validateUniqueId(identifier);
        CategoryDiscount cd = new CategoryDiscount(identifier, name, startDate, endDate, discountPercentage, targetCategory);
        savePromotion(cd);
    }

    /*
     * Registers a new bulk purchase discount promotion.
     *
     * @param identifier         the unique identifier
     * @param name               the promotion name
     * @param startDate          the start date of validity
     * @param endDate            the end date of validity
     * @param minQuantity        the minimum quantity of items required
     * @param discountPercentage the percentage discount to apply
     */
    public void registerBulkPurchaseDiscount(String identifier, String name, LocalDate startDate, LocalDate endDate, int minQuantity, double discountPercentage) {
        validateUniqueId(identifier);
        BulkPurchaseDiscount bd = new BulkPurchaseDiscount(identifier, name, startDate, endDate, minQuantity, discountPercentage);
        savePromotion(bd);
    }

    /*
     * Retrieves all registered promotions from persistence.
     *
     * @return a list of all promotions
     */
    public List<Promotion> listAllPromotions() {
        return promotionRepository.loadAll();
    }

    /*
     * Retrieves all active promotions valid for the current date.
     *
     * @return a list of active promotions
     */
    public List<Promotion> listActivePromotions() {
        List<Promotion> active = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Promotion p : promotionRepository.loadAll()) {
            if (p.isActive(today)) {
                active.add(p);
            }
        }
        return active;
    }

    /*
     * Finds the promotion that provides the highest monetary discount for a given sale.
     *
     * @param sale the sale to evaluate
     * @return the {@link Promotion} offering the best discount, or {@code null} if no promotion applies or maximum discount is zero
     */
    public Promotion findBestPromotionFor(Sale sale) {
        List<Promotion> activePromotions = listActivePromotions();
        Promotion bestPromotion = null;
        double maxDiscount = 0.0;

        for (Promotion promotion : activePromotions) {
            double currentDiscount = promotion.calculateDiscount(sale);
            if (currentDiscount > maxDiscount) {
                maxDiscount = currentDiscount;
                bestPromotion = promotion;
            }
        }

        return maxDiscount > 0.0 ? bestPromotion : null;
    }

}