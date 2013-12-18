package com.vaadin.demo.vspring.daointf;
import java.util.List;
import com.vaadin.demo.dashboard.domain.Person;

public interface PersonDao
{
    public void addPerson(Person person);
    public List<Person> getAllPerson();
    public void deletePerson(Integer PersonId);
}
