package com.gamezone.persistence;

import com.gamezone.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing Person entities.
 * Provides basic CRUD operations using a list.
 */
public class PersonRepository {

    private static final String FILE_PATH = "data/persons.csv";

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

    public void addPerson(Person person) {
        List<Person> persons = loadAll();
        persons.add(person);
        saveAll(persons);
    }

    public List<Person> getPeople() {
        return loadAll();
    }

    public Person findByIdentification(String identification) {
        return loadAll().stream()
                .filter(p -> p.getIdentification().equals(identification))
                .findFirst()
                .orElse(null);
    }

    public boolean removePerson(String identification) {
        List<Person> persons = loadAll();
        boolean removed = persons.removeIf(p -> p.getIdentification().equals(identification));
        if (removed) {
            saveAll(persons);
        }
        return removed;
    }
}

