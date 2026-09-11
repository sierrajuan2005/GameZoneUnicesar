package com.gamezone;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;
import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;
import com.gamezone.services.PersonService;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;
import com.gamezone.ui.ConsoleUI;


public class Main {
    public static void main(String[] args) {

        PersonRepository personRepository = new PersonRepository();
        ProductRepository productRepository = new ProductRepository();
        SaleRepository saleRepository = new SaleRepository();

        PersonService personService = new PersonService(personRepository);
        ProductService productService = new ProductService(productRepository);
        SaleService saleService = new SaleService(saleRepository, productService);


        preloadSellers(personService);

        ConsoleUI consoleUI = new ConsoleUI(personService, productService, saleService);
        consoleUI.start();
    }



    }
}
