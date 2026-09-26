package com.gamezone.persistence;

import com.gamezone.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for persisting and retrieving {@link Warranty} objects.
 * <p>
 * Warranties are stored in a CSV file located at {@code data/warranties.csv}.
 * This repository provides methods to:
 * <ul>
 *   <li>Add new warranties and persist them to the file.</li>
 *   <li>Retrieve all warranties currently loaded in memory.</li>
 *   <li>Find a warranty by its identifier.</li>
 *   <li>Load warranties from the CSV file into memory.</li>
 * </ul>
 * It supports both {@link BasicWarranty} and {@link ExtendedWarranty} types.
 */
public class WarrantyRepository {

    private static final String FILE_PATH = "data/warranties.csv";

    private List<Warranty> warranties;


    /**
     * Creates a WarrantyRepository with the required repositories.
     * Loads all warranties from the CSV file into memory.
     */
    public WarrantyRepository() {
        this.warranties = new ArrayList<>();
        loadAll();
    }

    /**
     * Adds a warranty to the repository and persists all warranties to the CSV file.
     *
     * @param warranty warranty to add
     */
    public void addWarranty(Warranty warranty){
        warranties.add(warranty);
        saveAll(warranties);
    }

    /**
     * Returns all warranties currently loaded in memory.
     *
     * @return list of warranties
     */
    public List<Warranty> getAllWarranties(){
        return  new ArrayList<>(warranties);
    }


    /**
     * Finds a warranty by its unique identifier.
     *
     * @param identifier warranty identifier
     * @return warranty with the given identifier, or null if not found
     */
    public Warranty findByIdentifier(String identifier){
        for (Warranty w : warranties){
            if (w.getIdentifier().equals(identifier)){
                return w;
            }
        }
        return null;
    }


    /**
     * Converts a warranty object into a CSV line representation.
     *
     * @param w warranty to convert
     * @return CSV-formatted string representing the warranty
     */
    private String convertToCsv (Warranty w){

        return w.getWarrantyType() + ";" +
                w.getIdentifier() + ";" +
                w.getProduct().getIdentifier() + ";" +
                w.getSale().getIdentifier() + ";" +
                w.getStartDate() + ";" +
                w.getEndDate();
        }
    }


    private WarrantyData convertFromCsv(String line){
        String[] data= line.split(";");
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
     * @param warranties list of warranties to save
     */
    private  void saveAll(List<Warranty> warranties){
        Path path = Paths.get(FILE_PATH);
        List<String> lines = new ArrayList<>();

        for (Warranty w : warranties){
            lines.add(convertToCsv(w));
        }
        try {
            Files.write(path, lines);
        }catch (IOException e){
            throw new RuntimeException("Error saving warranties.", e);
        }
    }


    /**
     * Loads all warranties from the CSV file into memory.
     * If the file does not exist, clears the current list.
     *
     * @return list of warranties loaded
     */
    public List<Warranty> loadAll(){
        Path path = Paths.get(FILE_PATH);

        try {
            if (!Files.exists(path)){
                warranties.clear();
                return warranties;
            }

            List<String>lines  = Files.readAllLines(path);
            warranties.clear();

            for (String line : lines){
                WarrantyData data = convertFromCsv(line);
            }
        }
        catch (IOException e){
            throw new RuntimeException("Error loading warranties.", e);
        }
        return warranties;
    }



}
