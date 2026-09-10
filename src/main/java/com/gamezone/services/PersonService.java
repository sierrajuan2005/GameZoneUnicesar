package com.gamezone.services;

import com.gamezone.domain.Person;
import com.gamezone.persistence.PersonRepository;

import java.util.List;

public class PersonService {

    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public PersonService() {
        this.personRepository = new PersonRepository();
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
