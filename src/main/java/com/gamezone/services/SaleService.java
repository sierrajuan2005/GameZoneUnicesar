package com.gamezone.services;
import com.gamezone.model.*;
import com.gamezone.persistence.SaleRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the business logic related to sales.
 */
public class SaleService {

    private SaleRepository saleRepository;
    private ProductService productService;
    private WarrantyService warrantyService;
    private PromotionService promotionService;

    /**
     * Creates a SaleService with the required repositories and services.
     *
     * @param saleRepository   repository used to store sales
     * @param productService   service used to manage products and stock
     * @param warrantyService  service used to generate warranties for consoles
     * @param promotionService service used to find the best applicable promotion
     */
    public SaleService(SaleRepository saleRepository, ProductService productService,
                       WarrantyService warrantyService, PromotionService promotionService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.warrantyService = warrantyService;
        this.promotionService = promotionService;
    }

    /**
     * Registers a sale, updates the stock of the sold products,
     * generates warranties where applicable, applies the best
     * available promotion to the sale, and persists everything.
     *
     * @param sale sale to register
     * @param productIdsWithExtendedWarranty identifiers of the products that should also receive an extended warranty
     */
    public void registerSale(Sale sale, List<String> productIdsWithExtendedWarranty) {
        List<Product> soldProducts = sale.getProducts();

        if (soldProducts.isEmpty()) {
            throw new IllegalArgumentException("A sale must contain at least one product.");
        }

        List<Product> storedProducts = productService.listProducts();

        for (Product soldProduct : soldProducts) {
            Product storedProduct = findProductById(storedProducts, soldProduct.getIdentifier());
            if (storedProduct.getAvailableQuantity() < 1) {
                throw new IllegalStateException("Insufficient stock for product: " + storedProduct.getTitle());
            }
        }

        for (Product soldProduct : soldProducts) {
            Product storedProduct = findProductById(storedProducts, soldProduct.getIdentifier());
            int newQuantity = storedProduct.getAvailableQuantity() - 1;
            productService.updateStock(storedProduct, newQuantity);
        }

        applyBestPromotion(sale);

        sale.getCustomer().addToPurchaseHistory(sale);
        saleRepository.save(sale);

        assignWarranties(sale, productIdsWithExtendedWarranty);
    }

    /**
     * Finds the best active promotion for the sale, if any, and
     * assigns its name and discount amount to the sale. If no
     * promotion applies, the sale keeps no discount.
     *
     * @param sale sale to evaluate and update
     */
    private void applyBestPromotion(Sale sale) {
        Promotion bestPromotion = promotionService.findBestPromotionFor(sale);

        if (bestPromotion != null) {
            double discount = bestPromotion.calculateDiscount(sale);
            sale.setAppliedPromotionName(bestPromotion.getName());
            sale.setDiscountAmount(discount);
        }
    }

    private void assignWarranties(Sale sale, List<String> productIdsWithExtendedWarranty) {
        List<String> extendedWarrantyIds = productIdsWithExtendedWarranty == null
                ? new ArrayList<>()
                : productIdsWithExtendedWarranty;

        for (Product product : sale.getProducts()) {
            if (!(product instanceof Console)) {
                continue;
            }

            warrantyService.assignBasicWarranty(product, sale, sale.getDate());

            if (extendedWarrantyIds.contains(product.getIdentifier())) {
                warrantyService.assignExtendedWarranty(product, sale, sale.getDate());
            }
        }
    }

    /**
     * Returns all registered sales.
     *
     * @return list of registered sales
     */
    public List<Sale> listSales() {
        return saleRepository.load();
    }

    /**
     * Returns the sales made by a specific customer.
     *
     * @param customer customer whose purchase history is requested
     * @return list of sales made by the customer
     */
    public List<Sale> getCustomerPurchaseHistory(Customer customer) {
        List<Sale> history = new ArrayList<>();

        for (Sale sale : saleRepository.load()) {
            if (sale.getCustomer().getIdentification().equals(customer.getIdentification())) {
                history.add(sale);
            }
        }

        return history;
    }

    /**
     * Returns the sales handled by a specific seller.
     *
     * @param seller seller whose sales history is requested
     * @return list of sales handled by the seller
     */
    public List<Sale> getSellerSalesHistory(Seller seller) {
        List<Sale> history = new ArrayList<>();

        for (Sale sale : saleRepository.load()) {
            if (sale.getSeller().getIdentification().equals(seller.getIdentification())) {
                history.add(sale);
            }
        }

        return history;
    }

    private Product findProductById(List<Product> products, String identifier) {
        for (Product product : products) {
            if (product.getIdentifier().equals(identifier)) {
                return product;
            }
        }

        throw new IllegalArgumentException("Product not found: " + identifier);
    }

    /**
     * Searches for a sale using its unique identifier.
     *
     * @param saleId the identifier of the sale to search for
     * @return the sale that matches the given identifier, or {@code null} if no sale is found
     */
    public Sale findSaleById(String saleId) {
        for (Sale sale : saleRepository.load()) {
            if (sale.getIdentifier().equals(saleId)) {
                return sale;
            }
        }

        return null;
    }
}