package com.gamezone.persistence;
import com.gamezone.model.Product;
import com.gamezone.model.Console;
import com.gamezone.model.VideoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
Handles product persistence using a CSV file.
 */

public class ProductRepository{

    private static final String FILE_PATH = "data/products.csv";

    /*
    Saves a product to the CSV file.
    @param product product to save
     */
    public void save(Product product){
        List<Product> products = loadAll();
        products.add(product);
        saveAll(products);
    }

    /*
    Loads all stored products.
    @return list of products
     */
    public List<Product> load(){
        return loadAll();
    }


    /*
    Saves all products to the CSV file.
    @param products list of products to save
     */
    public void saveAll(List<Product> products){
        Path path = Paths.get(FILE_PATH);
        try{
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();
            
            for(Product product : products){
                lines.add(convertToCsv(product));
            }
            Files.write(path, lines);
        } catch (IOException e){
            throw new RuntimeException("Error saving products.", e);
        }
    }

    /*
    Loads all products from the CSV file.
    @return list of stored products
     */
    public List<Product> loadAll(){
        Path path = Paths.get(FILE_PATH);
        List<Product> products = new ArrayList<>();

        if(!Files.exists(path)){
            return products;
        }

        try{
            List<String> lines=Files.readAllLines(path);

            for(String line: lines){
                if(!line.isBlank()){
                    Product product = convertFromCsv(line);
                    products.add(product);
                }
            }
        }catch (IOException e){
            throw new RuntimeException("Error loading products. ",e);
        }
        return products;
    }

    /*Converts a product into CSV format.
    @param product product to convert
    @return CSV representation of the product
     */
    private String convertToCsv(Product product) {

        if (product instanceof VideoGame videoGame) {
            return "VIDEO_GAME;"
                    + videoGame.getIdentifier() + ";"
                    + videoGame.getTitle() + ";"
                    + videoGame.getPrice() + ";"
                    + videoGame.getAvailableQuantity() + ";"
                    + videoGame.getPlatform() + ";"
                    + videoGame.getGenre() + ";"
                    + videoGame.getAgeRating();
        }

        if (product instanceof Console console) {
            return "CONSOLE;"
                    + console.getIdentifier() + ";"
                    + console.getTitle() + ";"
                    + console.getPrice() + ";"
                    + console.getAvailableQuantity() + ";"
                    + console.getBrand() + ";"
                    + console.getModel() + ";"
                    + console.getGeneration();
        }

        throw new IllegalArgumentException("Unsupported product type.");
    }

    /*
    Converts a CSV line into a product object.
    @param line CSV line representing a product
    @return converted product
     */
    private Product convertFromCsv(String line) {

        String[] data = line.split(";");

        String type = data[0];

        if (type.equals("VIDEO_GAME")) {

            return new VideoGame(
                    data[1],
                    data[2],
                    Double.parseDouble(data[3]),
                    Integer.parseInt(data[4]),
                    data[5],
                    data[6],
                    data[7]
            );
        }

        if (type.equals("CONSOLE")) {

            return new Console(
                    data[1],
                    data[2],
                    Double.parseDouble(data[3]),
                    Integer.parseInt(data[4]),
                    data[5],
                    data[6],
                    data[7]
            );
        }

        throw new IllegalArgumentException("Unknown product type: " + type);
    }
}