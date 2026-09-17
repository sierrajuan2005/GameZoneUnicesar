package com.gamezone.persistence;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    public void saveAll(List<Promotion> promotions) {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = new ArrayList<>();

        for (Promotion p : promotions) {
            lines.add(convertToCsv(p));
        }

        try {
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error saving promotions.", e);
        }
    }
}
