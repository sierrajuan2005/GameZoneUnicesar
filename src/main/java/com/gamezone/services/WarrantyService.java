package com.gamezone.services;

import com.gamezone.model.BasicWarranty;
import com.gamezone.model.Warranty;
import com.gamezone.model.WarrantyData;
import com.gamezone.model.Sale;
import com.gamezone.model.Product;
import com.gamezone.model.ExtendedWarranty;

import com.gamezone.persistence.SaleRepository;
import com.gamezone.persistence.WarrantyRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that manages the business logic for warranties.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Register warranties and persist them using {@link WarrantyRepository}.</li>
 *   <li>Reload warranties from CSV storage.</li>
 *   <li>Assign basic and extended warranties to products.</li>
 *   <li>Find warranties by identifier, product, or sale.</li>
 *   <li>List active warranties and those expiring soon.</li>
 *   <li>Cancel warranties when products are returned.</li>
 * </ul>
 * This class interacts with {@link ProductService}, {@link SaleRepository}, and {@link WarrantyRepository}.
 */
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;
    private final SaleRepository saleRepository;
    private final ProductService productService;

    private List<Warranty> warranties;

    /**
     * Constructs the service with required repositories and services.
     *
     * @param warrantyRepository repository for warranty persistence
     * @param saleRepository repository for sales
     * @param productService service for products
     */
    public WarrantyService(WarrantyRepository warrantyRepository, SaleRepository saleRepository, ProductService productService) {
        this.warrantyRepository = warrantyRepository;
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.warranties = new ArrayList<>();
        reloadWarranties();
    }

    /**
     * Builds a {@link Warranty} object from a {@link WarrantyData} DTO.
     *
     * @param data warranty data
     * @return constructed Warranty, or null if type is invalid
     */
    public Warranty buildWarranty(WarrantyData data) {
        Product product = productService.findByIdentifier(data.getProductIdentifier());
        Sale sale = saleRepository.findByIdentifier(data.getSaleIdentifier());

        if ("BASIC".equals(data.getType())) {
            return new BasicWarranty(data.getIdentifier(), product, sale ,data.getStartDate());
        } else if ("EXTENDED".equals(data.getType())) {
            return new ExtendedWarranty(data.getIdentifier(), product, sale ,data.getStartDate());
        }
        return null;
    }

    /**
     * Registers a warranty and persists it.
     *
     * @param warranty warranty to register
     * @throws IllegalArgumentException if the warranty is null
     */
    public void registerWarranty(Warranty warranty){
        if (warranty == null){
            throw new IllegalArgumentException("Warranty cannot be null");
        }
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
    }

    /**
     * Returns all warranties currently stored in memory.
     *
     * @return list of warranties
     */
    public List<Warranty> listWarranties(){
        return new ArrayList<>(warranties);
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
        return warranties.stream()
                .filter(w -> w.getIdentifier().equals(identifier))
                .findFirst()
                .orElse(null);
    }

    /**
     * Reloads warranties from persistence storage.
     */
    public void reloadWarranties(){
        List<String> lines = warrantyRepository.loadAll();
        warranties.clear();
        for (String line : lines){
            WarrantyData data = warrantyRepository.parseLine(line);
            Warranty warranty = buildWarranty(data);
            if (warranty != null){
                warranties.add(warranty);
            }
        }
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
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
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
        warranties.add(warranty);
        warrantyRepository.saveAll(warranties);
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
        return warranties.stream()
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
        return warranties.stream()
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
        return warranties.stream()
                .filter(w -> !w.getEndDate().isBefore(today) && !w.getEndDate().isAfter(limit))
                .collect(Collectors.toList());
    }

    /**
     * Cancels warranties for a given product in a specific sale.
     * <p>
     * Removes the warranty from the internal list and persistence,
     * and returns the refundable cost:
     * <ul>
     *   <li>0 for basic warranty</li>
     *   <li>Additional cost for extended warranty</li>
     * </ul>
     *
     * @param productId identifier of the product
     * @param saleId identifier of the sale
     * @return refundable cost of the cancelled warranty
     */
    public double cancelWarranties(String productId, String saleId) {
        Warranty warranty = findWarrantyByProduct(productId, saleId);
        if (warranty == null) {
            return 0.0;
        }

        double refund = 0.0;
        if (warranty instanceof ExtendedWarranty) {
            refund = ((ExtendedWarranty) warranty).getAdditionalCost();
        }

        warranties.remove(warranty);
        warrantyRepository.saveAll(warranties);

        return refund;
    }
}