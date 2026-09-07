package com.gamezone.ui;

import com.gamezone.service.PersonService;

import java.util.Scanner;

public class ConsoleUI {

    private PersonService personService;
    private Scanner scanner;

    public ConsoleUI(PersonService personService) {

        this.personService = personService;
        this.scanner = new scanner(System.in);
    }

    public void start(){

        int option;

        do {

            System.out.println("=== Person Menu === ");
            System.out.println("1. Register Customer ");
            System.out.println("2. Register Seller ");
            System.out.println("3. List People ");
            System.out.println("4. Find Person by Identification ");
            System.out.println("5. Remove Person ");
            System.out.println("0. Exit ");

            System.out.println("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option){

                case 1:
                    //registerCustomer();
                    break;
                case 2:
                    //registerSeller();
                    break;
                case 3:
                    //listPeople();
                    break;
                case 4:
                    //findPerson();
                    break;
                case 5:
                    //removePerson();
                    break;
            }
        }while (option != 0);
    }
}
