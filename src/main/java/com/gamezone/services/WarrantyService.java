package com.gamezone.services;

import com.gamezone.model.*;
import com.gamezone.persistence.WarrantyRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service class that handles the business logic related to warranties.
 * <p>
 * This class provides methods to:
 * <ul>
 *   <li>Register and list warranties.</li>
 *   <li>Find warranties by identifier or by product and sale.</li>
 *   <li>Reload warranties from persistence.</li>
 *   <li>Assign basic and extended warranties to products.</li>
 *   <li>List active warranties and those expiring soon.</li>
 * </ul>
 * It interacts with {@link WarrantyRepository} for persistence operations.
 */
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    /**
     * Creates a WarrantyService with the required repository.
     *
     * @param warrantyRepository repository used to store and retrieve warranties
     */
    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }


    /**
     * Registers a warranty in the repository.
     *
     * @param warranty warranty to register
     * @throws IllegalArgumentException if the warranty is null
     */
    public void registerWarranty(Warranty warranty){
        if (warranty == null){
            throw new IllegalArgumentException("Warranty cannot be null");
        }
        warrantyRepository.addWarranty(warranty);
    }

    /**
     * Returns all warranties currently stored.
     *
     * @return list of warranties
     */
    public List<Warranty> listWarranties(){
        return warrantyRepository.getAllWarranties();
    }


    /**
     * Finds a warranty by its identifier.
     *
     * @param identifier warranty identifier
     * @return warranty with the given identifier, or null if not found
     * @throws IllegalArgumentException if the identifier is null or empty
     */
    public Warranty findWarrantyByIdentifier(String identifier){
        if (identifier == null || identifier.isEmpty()){
            throw new IllegalArgumentException("Identifier cannot be null or empty.");
        }
        return warrantyRepository.findByIdentifier(identifier);
    }


    /**
     * Reloads warranties from the persistence layer.
     *
     * @return list of warranties loaded from storage
     */
    public List<Warranty> reloadWarranties(){
        return warrantyRepository.loadAll();
    }


    /**
     * Assigns a basic warranty to a product and persists it.
     *
     * @param product   product covered by the warranty
     * @param sale      sale associated with the warranty
     * @param startDate start date of the warranty
     * @return created basic warranty
     */
    public BasicWarranty assignBasicWarranty(Product product, Sale sale, LocalDate startDate) {
        BasicWarranty warranty = new BasicWarranty(product.getIdentifier(), product, sale, startDate);
        warrantyRepository.addWarranty(warranty);
        return warranty;
    }


    /**
     * Assigns an extended warranty to a product and persists it.
     *
     * @param product   product covered by the warranty
     * @param sale      sale associated with the warranty
     * @param startDate start date of the warranty
     * @return created extended warranty
     */
    public ExtendedWarranty assignExtendedWarranty(Product product, Sale sale, LocalDate startDate) {
        ExtendedWarranty warranty = new ExtendedWarranty(product.getIdentifier(), product, sale, startDate);
        warrantyRepository.addWarranty(warranty);
        return warranty;
    }


    /**
     * Finds a warranty by product and sale identifiers.
     *
     * @param productIdentifier identifier of the product
     * @param saleIdentifier    identifier of the sale
     * @return warranty matching the product and sale, or null if not found
     */
    public Warranty findWarrantyByProduct(String productIdentifier, String saleIdentifier) {
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> w.getProduct().getIdentifier().equals(productIdentifier)
                        && w.getSale().getIdentifier().equals(saleIdentifier))
                .findFirst()
                .orElse(null);
    }


    /**
     * Returns all warranties that are currently active.
     *
     * @return list of active warranties
     */
    public List<Warranty> listActiveWarranties() {
        LocalDate today = LocalDate.now();
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> w.isActive(today))
                .collect(Collectors.toList());
    }

    /**
     * Returns warranties that expire within a given number of days.
     *
     * @param daysAhead number of days ahead to check
     * @return list of warranties expiring soon
     */
    public List<Warranty> listWarrantiesExpiringSoon(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(daysAhead);
        return warrantyRepository.getAllWarranties().stream()
                .filter(w -> !w.getEndDate().isBefore(today) && !w.getEndDate().isAfter(limit))
                .collect(Collectors.toList());
    }


}
