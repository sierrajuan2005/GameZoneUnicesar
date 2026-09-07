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
}
