package com.gamezone.service;

import com.gamezone.model.Person;
import persistence.PersonRepository;

import java.util.List;

public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void addPerson(Person person){

        personRepository.addPerson(person);
    }

    public List<Person> getAllPeople(){

        return personRepository.getPeople();
    }

    public Person findPersonByIdentification(String identification){

        return personRepository.findByIdentification(identification);
    }

    public void removePerson(Person person){

        personRepository.removePerson(person);
    }

}
