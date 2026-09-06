package com.gamezone.persistence;
import com.gamezone.domain.Product;
import com.gamezone.domain.Console;
import com.gamezone.domain.VideoGame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository{

    private static final String FILE_PATH = "data/products.csv";


    public void save(Product product){
        List<Product> products = loadAll();
        products.add(product);
        saveAll(products);
    }


    public List<Product> load(){
        return loadAll();
    }

    public void saveAll(List<Product> products){
        Path path = Paths.get(FILE_PATH);
        try{
            Files.createDirectories(path.getParent());
            List<String> lines = new ArrayList<>();
            
            for(Product product : products){
                lines.add(convertToCsv(product));
            }
        } catch (IOException e){
            throw new RuntimeException("Error saving products.", e);
        }
    }

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