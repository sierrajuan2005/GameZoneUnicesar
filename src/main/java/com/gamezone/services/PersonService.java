package com.gamezone.services;

import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Customer;
import com.gamezone.model.Person;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;
import com.gamezone.validation.PersonValidator;

import java.util.List;

/**
 * Service layer for Person operations.
 * Delegates actions to PersonRepository and validations to PersonValidator.
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
     * Only customers have an email; for other roles the email is passed as null.
     *
     * @param person person to add
     * @throws com.gamezone.exceptions.InvalidDataException if person data is invalid
     * @throws com.gamezone.exceptions.BusinessRuleException if a person with the same ID already exists
     */
    public void addPerson(Person person) {
        String email = (person instanceof Customer customer) ? customer.getEmail() : null;

        PersonValidator.validatePersonData(
                person.getIdentification(),
                person.getName(),
                email,
                person.getPhone()
        );

        List<Person> existing = personRepository.getPeople();
        PersonValidator.validateUniquePerson(existing, person.getIdentification());

        personRepository.addPerson(person);
    }

    /**
     * Returns all persons.
     *
     * @return list of persons
     */
    public List<Person> getAllPeople() {
        return personRepository.getPeople();
    }

    /**
     * Finds a person by identification.
     *
     * @param identification unique identifier
     * @return matching person
     * @throws ResourceNotFoundException if no person is found
     */
    public Person findPersonByIdentification(String identification) {
        Person person = personRepository.findByIdentification(identification);
        if (person == null) {
            throw new ResourceNotFoundException("Person", identification);
        }
        return person;
    }

    /**
     * Removes a person by identification.
     *
     * @param identification unique ID of the person
     * @return true if removed
     * @throws ResourceNotFoundException if no person is found
     */
    public boolean removePerson(String identification) {
        Person person = personRepository.findByIdentification(identification);
        if (person == null) {
            throw new ResourceNotFoundException("Person", identification);
        }
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