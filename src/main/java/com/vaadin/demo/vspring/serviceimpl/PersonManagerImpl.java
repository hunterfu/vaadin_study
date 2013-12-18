package com.vaadin.demo.vspring.serviceimpl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vaadin.demo.vspring.daointf.PersonDao;
import com.vaadin.demo.dashboard.domain.Person;
import com.vaadin.demo.vspring.serviceintf.PersonManager;;
@Service
public class PersonManagerImpl implements PersonManager {
    @Autowired
    private PersonDao employeeDAO;
    @Override
    @Transactional
    public void addPerson(Person employee) {
        employeeDAO.addPerson(employee);
    }
    @Override
    @Transactional
    public List<Person> getAllPerson() {
        return employeeDAO.getAllPerson();
    }
    @Override
    @Transactional
    public void deletePerson(Integer employeeId) {
        employeeDAO.deletePerson(employeeId);
    }
    public void setEmployeeDAO(PersonDao employeeDAO) {
        this.employeeDAO = employeeDAO;
    }
}
