package persistence;

import com.gamezone.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private List<Person> people;

    public PersonRepository() {
        this.people = new ArrayList<>();
    }

    public void addPerson(Person person){

        people.add(person);
    }

    public List<Person> getPeople(){

        return people;
    }

    public Person findByIdentification(String identification){

        for (Person p : people){
            if (p.getIdentification().equals(identification)){
                return p;
            }
        }
        return null;
    }
}
