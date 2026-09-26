package com.gamezone.persistence;

import com.gamezone.model.Warranty;
import com.gamezone.model.WarrantyData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for persisting and retrieving {@link Warranty} objects in CSV format.
 * <p>
 * This repository provides methods to:
 * <ul>
 *   <li>Convert {@link Warranty} objects to CSV lines and vice versa.</li>
 *   <li>Save all warranties to a CSV file.</li>
 *   <li>Load raw CSV lines from the file.</li>
 *   <li>Parse a single CSV line into a {@link WarrantyData} DTO.</li>
 * </ul>
 * The repository does not maintain an internal list of warranties; that responsibility belongs to the service layer.
 */
public class WarrantyRepository {

    /** Path to the CSV file where warranties are persisted. */
    private static final String FILE_PATH = "data/warranties.csv";

    /**
     * Converts a {@link Warranty} object into a CSV line representation.
     *
     * @param w warranty to convert
     * @return CSV-formatted string representing the warranty
     */
    private String convertToCsv(Warranty w) {
        return w.getWarrantyType() + ";" +
                w.getIdentifier() + ";" +
                w.getProduct().getIdentifier() + ";" +
                w.getSale().getIdentifier() + ";" +
                w.getStartDate() + ";" +
                w.getEndDate();
    }

    /**
     * Converts a CSV line into a {@link WarrantyData} DTO.
     *
     * @param line CSV line to parse
     * @return WarrantyData object containing parsed values
     */
    private WarrantyData convertFromCsv(String line) {
        String[] data = line.split(";");
        return new WarrantyData(
                data[0],
                data[1],
                data[2],
                data[3],
                LocalDate.parse(data[4]),
                LocalDate.parse(data[5])
        );
    }

    /**
     * Saves all warranties to the CSV file.
     *
     * @param warranties list of warranties to persist
     */
    public void saveAll(List<Warranty> warranties) {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = new ArrayList<>();

        for (Warranty w : warranties) {
            lines.add(convertToCsv(w));
        }

        try {
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException("Error saving warranties.", e);
        }
    }

    /**
     * Loads all warranties as raw CSV lines from the file.
     * If the file does not exist, returns an empty list.
     *
     * @return list of CSV lines representing warranties
     */
    public List<String> loadAll() {
        Path path = Paths.get(FILE_PATH);
        try {
            if (!Files.exists(path)) {
                return new ArrayList<>();
            }
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException("Error loading warranties.", e);
        }
    }

    /**
     * Parses a single CSV line into a {@link WarrantyData} DTO.
     *
     * @param line CSV line to parse
     * @return WarrantyData object containing parsed values
     */
    public WarrantyData parseLine(String line) {
        return convertFromCsv(line);
    }
}