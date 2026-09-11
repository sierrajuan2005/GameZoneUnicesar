package com.gamezone.persistence;

import com.gamezone.model.Customer;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for managing Person entities.
 * Provides basic CRUD operations using a list.
 */
public class PersonRepository {

    private List<Person> people;

    /** Creates an empty repository. */
    public PersonRepository() {
        this.people = new ArrayList<>();
    }

    /**
     * Adds a person to the repository.
     *
     * @param person person to add
     */
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
