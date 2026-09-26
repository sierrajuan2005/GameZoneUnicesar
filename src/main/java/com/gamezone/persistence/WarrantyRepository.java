package com.gamezone.persistence;

import com.gamezone.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WarrantyRepository {

    private static final String FILE_PATH = "data/warranties.csv";

    public WarrantyRepository() {
        loadAll();
    }


    private String convertToCsv (Warranty w){

        return w.getWarrantyType() + ";" +
                w.getIdentifier() + ";" +
                w.getProduct().getIdentifier() + ";" +
                w.getSale().getIdentifier() + ";" +
                w.getStartDate() + ";" +
                w.getEndDate();
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

    public WarrantyData parseLine(String line) {
        return convertFromCsv(line);
    }



}
