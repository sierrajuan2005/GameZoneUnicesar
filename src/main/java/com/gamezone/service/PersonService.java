package com.gamezone.service;

import com.gamezone.model.Person;
import persistence.PersonRepository;

public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPerson(Person person){

        personRepository.addPerson(person);
    }
}
