package com.gamezone;

import com.gamezone.service.PersonService;
import com.gamezone.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {

        PersonService service = new PersonService();

        ConsoleUI ui = new ConsoleUI(service);

        ui.start();

    }
}
