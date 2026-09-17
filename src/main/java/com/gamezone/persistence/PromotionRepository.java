package com.gamezone.persistence;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;

public class PromotionRepository {

    private static final String FILE_PATH = "data/promotions.csv";

    private String convertToCsv(Promotion p) {
        if (p instanceof PercentageDiscount pd) {
            return "PERCENTAGE;" + pd.getIdentifier() + ";" +
                    pd.getName() + ";" +
                    pd.getStartDate() + ";" +
                    pd.getEndDate() + ";" +
                    pd.getDiscountPercentage();
        } else if (p instanceof CategoryDiscount cd) {
            return "CATEGORY;" + cd.getIdentifier() + ";" +
                    cd.getName() + ";" +
                    cd.getStartDate() + ";" +
                    cd.getEndDate() + ";" +
                    cd.getDiscountPercentage() + ";" +
                    cd.getTargetCategory();
        } else if (p instanceof BulkPurchaseDiscount bd) {
            return "BULK;" + bd.getIdentifier() + ";" +
                    bd.getName() + ";" +
                    bd.getStartDate() + ";" +
                    bd.getEndDate() + ";" +
                    bd.getMinQuantity() + ";" +
                    bd.getDiscountPercentage();
        }
        return "";
    }
}
