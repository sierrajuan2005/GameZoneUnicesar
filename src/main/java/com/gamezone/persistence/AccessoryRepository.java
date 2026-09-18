package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.*;
import java.util.ArrayList;
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

    public List<Accessory> loadAll() {
        List<Accessory> accessories = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return accessories;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String type = parts[0];
                String id = parts[1];
                String title = parts[2];
                double price = Double.parseDouble(parts[3]);
                int availableQuantity = Integer.parseInt(parts[4]);

                switch (type) {
                    case "Controller" -> {
                        String connectionType = parts[5];
                        accessories.add(new Controller(id, title, price, availableQuantity, List.of(), connectionType));
                    }
                    case "Cable" -> {
                        double length = Double.parseDouble(parts[5]);
                        String connectorType = parts[6];
                        accessories.add(new Cable(id, title, price, availableQuantity, List.of(), length, connectorType));
                    }
                    case "Memory" -> {
                        int capacity = Integer.parseInt(parts[5]);
                        String memoryType = parts[6];
                        accessories.add(new Memory(id, title, price, availableQuantity, List.of(), capacity, memoryType));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accessories;
    }
}
