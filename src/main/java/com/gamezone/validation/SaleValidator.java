
package com.gamezone.validation;

import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Customer;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;

import java.util.List;

/**
 * Provides validation methods for sales.
 */
public final class SaleValidator {

    private SaleValidator() {
    }

    /**
     * Validates the customer, seller and products involved in a sale.
     */
    public static void validateSaleData(
            Customer customer,
            Seller seller,
            List<Product> products) {

        if (customer == null) {
            throw new InvalidDataException(
                    "customer", "no puede ser nulo");
        }

        if (seller == null) {
            throw new InvalidDataException(
                    "seller", "no puede ser nulo");
        }

        if (products == null || products.isEmpty()) {
            throw new InvalidDataException(
                    "products", "debe contener al menos un producto");
        }

        for (Product product : products) {
            if (product == null) {
                throw new InvalidDataException(
                        "products", "no puede contener productos nulos");
            }
        }
    }

    /**
     * Checks whether the requested sale exists.
     */
    public static void validateSaleExists(Sale sale, String saleId) {
        if (sale == null) {
            throw new ResourceNotFoundException("venta", saleId);
        }
    }
}
