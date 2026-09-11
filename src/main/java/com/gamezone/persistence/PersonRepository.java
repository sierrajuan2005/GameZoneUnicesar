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

    public void addPerson(Person person){

        people.add(person);
    }

    /**
     * Returns all persons in the repository.
     *
     * @return list of persons
     */
    public List<Person> getPeople(){

        return people;
    }

    /**
     * Finds a person by identification.
     *
     * @param identification unique identifier
     * @return matching person or null if not found
     */
    public Person findByIdentification(String identification){

        for (Person p : people){
            if (p.getIdentification().equals(identification)){
                return p;
            }
        }
        return null;
    }

    /**
     * Removes a person from the repository.
     *
     * @param person person to remove
     */
    public void removePerson(Person person){

        people.remove(person);
    }
}
