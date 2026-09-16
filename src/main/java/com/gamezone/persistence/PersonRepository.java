package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Person entities.
 * Provides persistence to a CSV file and methods to save and load data.
 */
public class PersonRepository {

    private static final String FILE_PATH = "data/persons.csv";

    /**
     * Saves all persons to the CSV file.
     *
     * @param persons list of persons to persist
     */
    public void saveAll(List<Person> persons) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Person p : persons) {
                if (p instanceof Customer c) {
                    writer.println("CUSTOMER;" + c.getIdentification() + ";" + c.getName() + ";" + c.getPhone() + ";" + c.getEmail());
                } else if (p instanceof Seller s) {
                    writer.println("SELLER;" + s.getIdentification() + ";" + s.getName() + ";" + s.getPhone() + ";" + s.getEmployeeCode() + ";" + s.getWorkShift());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving persons", e);
        }
    }

    /**
     * Loads all persons from the CSV file.
     *
     * @return list of persons, empty if file does not exist
     */
    public List<Person> loadAll() {
        List<Person> persons = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return persons;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String type = parts[0];

                if ("CUSTOMER".equals(type)) {
                    persons.add(new Customer(parts[1], parts[2], parts[3], parts[4]));
                } else if ("SELLER".equals(type)) {
                    persons.add(new Seller(parts[1], parts[2], parts[3], parts[4], parts[5]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading persons", e);
        }

        return persons;
    }

    /**
     * Adds a new person to the repository and persists changes.
     *
     * @param person person to add
     */
    public void addPerson(Person person) {
        List<Person> persons = loadAll();
        persons.add(person);
        saveAll(persons);
    }

    /**
     * Retrieves all persons from the repository.
     *
     * @return list of persons
     */
    public List<Person> getPeople() {
        return loadAll();
    }

    /**
     * Finds a person by identification.
     *
     * @param identification unique ID of the person
     * @return person if found, null otherwise
     */
    public Person findByIdentification(String identification) {
        return loadAll().stream()
                .filter(p -> p.getIdentification().equals(identification))
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes a person from the repository by identification and persists changes.
     *
     * @param identification unique ID of the person to remove
     * @return true if the person was removed, false if not found
     */
    public boolean removePerson(String identification) {
        List<Person> persons = loadAll();
        boolean removed = persons.removeIf(p -> p.getIdentification().equals(identification));
        if (removed) {
            saveAll(persons);
        }
        return removed;
    }
}

