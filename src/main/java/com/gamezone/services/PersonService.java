package com.gamezone.services;

import com.gamezone.model.Person;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.List;

/**
 * Service layer for Person operations.
 * Delegates actions to PersonRepository.
 */
public class PersonService {

    private final PersonRepository personRepository;

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
     * Removes a person by identification.
     *
     * @param identification unique ID of the person
     * @return true if removed, false otherwise
     */
    public boolean removePerson(String identification) {
        return personRepository.removePerson(identification);
    }

    /**
     * Preloads default sellers if none exist in the repository.
     *
     * @param personService service instance to use for adding sellers
     */
    public static void preloadSellers(PersonService personService) {
        boolean hasSellers = personService.getAllPeople().stream()
                .anyMatch(p -> p instanceof Seller);

        if (hasSellers) {
            return;
        }

        personService.addPerson(new Seller("Laura Gomez", "1007", "3001234567", "EMP007", "Morning"));
        personService.addPerson(new Seller("Carlos Perez", "1008", "3009876543", "EMP008", "Afternoon"));
        personService.addPerson(new Seller("Maria Rodriguez", "1009", "3004567890", "EMP009", "Night"));
    }
}
