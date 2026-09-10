package com.gamezone.services;

import com.gamezone.persistence.ProductRepository;
import com.gamezone.persistence.SaleRepository;

public class SaleService {
    private SaleRepository saleRepository;
    private ProductRepository productRepository;
    private PersonService personService;


    public SaleService(SaleRepository saleRepository, ProductRepository productRepository, PersonService personService) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.personService = personService;
    }


}
