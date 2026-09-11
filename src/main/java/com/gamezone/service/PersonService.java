package com.gamezone.service;

import com.gamezone.model.Person;
import com.gamezone.persistence.PersonRepository;

import java.util.List;

/**
 * Service layer for Person operations.
 * Delegates actions to PersonRepository.
 */
public class PersonService {

    private PersonRepository personRepository;

    /** Creates service with given repository. */
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /** Creates service with a new repository. */
    public PersonService() {
        this.personRepository = new PersonRepository();
    }

    /**
     * Adds a person.
     *
     * @param person person to add
     */
    public void addPerson(Person person){

        personRepository.addPerson(person);
    }

    /**
     * Returns all persons.
     *
     * @return list of persons
     */
    public List<Person> getAllPeople(){

        return personRepository.getPeople();
    }

    /**
     * Finds a person by identification.
     *
     * @param identification unique identifier
     * @return matching person or null
     */
    public Person findPersonByIdentification(String identification){

        return personRepository.findByIdentification(identification);
    }

    /**
     * Removes a person.
     *
     * @param person person to remove
     */
    public void removePerson(Person person){

        personRepository.removePerson(person);
    }

}
