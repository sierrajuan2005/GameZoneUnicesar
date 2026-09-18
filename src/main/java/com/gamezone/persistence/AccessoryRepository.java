package com.gamezone.persistence;

import com.gamezone.model.Accessory;
import com.gamezone.model.Cable;
import com.gamezone.model.Controller;
import com.gamezone.model.Memory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Repository for persisting {@link Accessory} objects in a CSV file.
 * Provides methods to save and load accessories from disk.
 */
public class AccessoryRepository {

    /* Path to the CSV file where accessories are stored. */
    private static final String FILE_PATH = "data/accessories.csv";

    /*
     * Constructs a new AccessoryRepository.
     * No initialization is required beyond the file path constant.
     */
    public AccessoryRepository() {
    }

    /**
     * Saves all accessories to the CSV file.
     * Each accessory is serialized with its type and attributes.
     *
     * @param accessories list of accessories to persist
     */
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

    /*
     * Loads all accessories from the CSV file.
     * Uses the first column as a discriminator to instantiate
     * {@link Controller}, {@link Cable}, or {@link Memory}.
     *
     * @return list of accessories loaded from file, or empty list if file does not exist
     */
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