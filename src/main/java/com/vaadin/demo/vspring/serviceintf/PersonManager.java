package com.vaadin.demo.vspring.serviceintf;
import java.util.List;
import com.vaadin.demo.dashboard.domain.Person;
public interface PersonManager {
    public void addPerson(Person Person);
    public List<Person> getAllPerson();
    public void deletePerson(Integer PersonId);
}
