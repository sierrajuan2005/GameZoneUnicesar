package com.gamezone.persistence;

import com.gamezone.model.Accessory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccessoryRepository {

    private static final String FILE_PATH = "data/accessories.csv";

    public AccessoryRepository() {
    }

    public void saveAll(List<Accessory> accessories) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Accessory accessory : accessories) {
                String type = accessory.getClass().getSimpleName();
                writer.println(type + ";" + accessory.getIdentifier() + ";" +
                        accessory.getTitle() + ";" + accessory.getPrice() + ";" +
                        accessory.getAvailableQuantity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
