package com.gamezone.validation;

import com.gamezone.exceptions.BusinessRuleException;
import com.gamezone.exceptions.InvalidDataException;
import com.gamezone.exceptions.ResourceNotFoundException;
import com.gamezone.model.Person;

import java.util.List;

/**
 * Provides validation methods for person data and operations.
 */
public final class PersonValidator {

    /**
     * Prevents instantiation of this utility class.
     */
    private PersonValidator() {
    }

    /**
     * Validates the required person data and email format.
     *
     * @param id person's identifier
     * @param name person's name
     * @param email person's email
     * @param phone person's phone number
     * @throws InvalidDataException if any required field is invalid
     */
    public static void validatePersonData(
            String id, String name, String email, String phone) {

        if (id == null || id.isBlank()) {
            throw new InvalidDataException(
                    "id", "es obligatorio y no puede estar vacio");
        }

        if (name == null || name.isBlank()) {
            throw new InvalidDataException(
                    "name", "es obligatorio y no puede estar vacio");
        }

        if (email == null || email.isBlank()) {
            throw new InvalidDataException(
                    "email", "es obligatorio y no puede estar vacio");
        }

        if (!email.contains("@")
                || !email.substring(email.indexOf('@')).contains(".")) {
            throw new InvalidDataException(
                    "email", "debe contener arroba y un punto despues de ella");
        }

        if (phone == null || phone.isBlank()) {
            throw new InvalidDataException(
                    "phone", "es obligatorio y no puede estar vacio");
        }
    }

    /**
     * Checks whether the specified person exists.
     *
     * @param person person to validate
     * @param personId identifier used to locate the person
     * @throws ResourceNotFoundException if the person is null
     */
    public static void validatePersonExists(Person person, String personId) {
        if (person == null) {
            throw new ResourceNotFoundException("persona", personId);
        }
    }

    /**
     * Checks whether a person identifier is already in use.
     *
     * @param existing existing people
     * @param newId identifier to check
     * @throws InvalidDataException if the list or identifier is invalid
     * @throws BusinessRuleException if the identifier already exists
     */
    public static void validateUniquePerson(
            List<Person> existing, String newId) {

        if (existing == null) {
            throw new InvalidDataException(
                    "existing", "la lista de personas no puede ser nula");
        }

        if (newId == null || newId.isBlank()) {
            throw new InvalidDataException(
                    "newId", "es obligatorio y no puede estar vacio");
        }

        for (Person person : existing) {
            if (person != null
                    && newId.equals(person.getIdentification())) {
                throw new BusinessRuleException(
                        "Ya existe una persona con identificador " + newId + ".");
            }
        }
    }
}