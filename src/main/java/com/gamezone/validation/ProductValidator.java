package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Product;

/**
 * Provides validation methods for product data and operations.
 */
public final class ProductValidator {

    /**
     * Prevents instantiation of this utility class.
     */
    private ProductValidator() {
    }

    /**
     * Validates the required product data, price, and stock.
     *
     * @param id product identifier
     * @param title product title
     * @param price product price
     * @param stock available product quantity
     * @throws InvalidDataException if any field is invalid
     */
    public static void validateProductData(
            String id, String title, double price, int stock) {

        if (id == null || id.isBlank()) {
            throw new InvalidDataException(
                    "id", "es obligatorio y no puede estar vacio");
        }

        if (title == null || title.isBlank()) {
            throw new InvalidDataException(
                    "title", "es obligatorio y no puede estar vacio");
        }

        if (!Double.isFinite(price) || price <= 0) {
            throw new InvalidDataException(
                    "price", "debe ser un numero mayor que cero");
        }

        if (stock < 0) {
            throw new InvalidDataException(
                    "stock", "no puede ser negativo");
        }
    }

    /**
     * Checks whether enough product units are available.
     *
     * @param product product to validate
     * @param requestedQuantity quantity requested
     * @throws ResourceNotFoundException if the product is null
     * @throws InvalidDataException if the requested quantity is invalid
     * @throws BusinessRuleException if the available stock is insufficient
     */
    public static void validateStockAvailability(
            Product product, int requestedQuantity) {

        validateProductExists(
                product, product == null ? null : product.getIdentifier());

        if (requestedQuantity <= 0) {
            throw new InvalidDataException(
                    "requestedQuantity", "debe ser mayor que cero");
        }

        if (product.getAvailableQuantity() < requestedQuantity) {
            throw new BusinessRuleException(
                    "Stock insuficiente para el producto "
                            + product.getIdentifier()
                            + ". Disponible: "
                            + product.getAvailableQuantity()
                            + ", solicitado: "
                            + requestedQuantity + ".");
        }
    }

    /**
     * Checks whether the specified product exists.
     *
     * @param product product to validate
     * @param productId identifier used to locate the product
     * @throws ResourceNotFoundException if the product is null
     */
    public static void validateProductExists(
            Product product, String productId) {

        if (product == null) {
            throw new ResourceNotFoundException("producto", productId);
        }
    }
}