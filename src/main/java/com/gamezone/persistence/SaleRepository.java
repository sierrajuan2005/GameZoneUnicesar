package com.gamezone.persistence;
import com.gamezone.domain.Sale;
import com.gamezone.domain.Customer;
import com.gamezone.domain.Seller;
import com.gamezone.domain.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class SaleRepository {

    private static final String FILE_PATH = "data/sales.txt";

    private PersonRepository personRepository;
    private ProductRepository productRepository;

    public SaleRepository(PersonRepository personRepository, ProductRepository productRepository) {
        this.personRepository = personRepository;
        this.productRepository = productRepository;
    }


    public void save(Sale sale) {
        List<Sale> sales = loadAll();
        sales.add(sale);
        saveAll(sales);
    }






}
