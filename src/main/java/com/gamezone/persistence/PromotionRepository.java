package com.gamezone.persistence;

import com.gamezone.model.BulkPurchaseDiscount;
import com.gamezone.model.CategoryDiscount;
import com.gamezone.model.PercentageDiscount;
import com.gamezone.model.Promotion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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

    private Promotion convertFromCsv(String line) {
        String[] data = line.split(";");
        if (data.length < 6) return null;

        String type = data[0];
        String identifier = data[1];
        String name = data[2];
        LocalDate startDate = LocalDate.parse(data[3]);
        LocalDate endDate = LocalDate.parse(data[4]);

        switch (type) {
            case "PERCENTAGE":
                double pDiscount = Double.parseDouble(data[5]);
                return new PercentageDiscount(identifier, name, startDate, endDate, pDiscount);

            case "CATEGORY":
                double cDiscount = Double.parseDouble(data[5]);
                String category = data[6];
                return new CategoryDiscount(identifier, name, startDate, endDate, cDiscount, category);

            case "BULK":
                int minQty = Integer.parseInt(data[5]);
                double bDiscount = Double.parseDouble(data[6]);
                return new BulkPurchaseDiscount(identifier, name, startDate, endDate, minQty, bDiscount);

            default:
                return null;
        }
    }
}
