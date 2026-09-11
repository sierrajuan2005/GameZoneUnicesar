package com.gamezone.ui;
import com.gamezone.model.Console;
import com.gamezone.model.Customer;
import com.gamezone.model.Person;
import com.gamezone.model.Product;
import com.gamezone.model.Sale;
import com.gamezone.model.Seller;
import com.gamezone.model.VideoGame;
import com.gamezone.services.PersonService;
import com.gamezone.services.ProductService;
import com.gamezone.services.SaleService;


public class ConsoleUI {

    private final PersonService personService;
    private final ProductService productService;
    private final SaleService saleService;
    private final Scanner scanner;

    /**
     * Creates the console user interface.
     *
     * @param personService  service used to manage customers and sellers
     * @param productService service used to manage products
     * @param saleService    service used to manage sales
     */
    public ConsoleUI(PersonService personService, ProductService productService, SaleService saleService) {
        this.personService = personService;
        this.productService = productService;
        this.saleService = saleService;
        this.scanner = new Scanner(System.in);
    }


}


