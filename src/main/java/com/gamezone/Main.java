package com.gamezone;

import com.gamezone.services.PersonService;
import com.gamezone.ui.ConsoleUI;

/**
 * Entry point of the GameZone application.
 * Initializes service and starts console UI.
 */
public class Main {

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {

        PersonService service = new PersonService();

        ConsoleUI ui = new ConsoleUI(service);

        ui.start();

    }
}
