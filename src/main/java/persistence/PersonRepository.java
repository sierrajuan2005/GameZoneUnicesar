package persistence;

import com.gamezone.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private List<Person> people;

    public PersonRepository() {
        this.people = new ArrayList<>();
    }
}
