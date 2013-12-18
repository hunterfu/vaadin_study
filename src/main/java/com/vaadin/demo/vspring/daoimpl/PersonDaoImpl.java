package com.vaadin.demo.vspring.daoimpl;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.vaadin.demo.dashboard.domain.Person;
import com.vaadin.demo.vspring.daointf.PersonDao;

@Repository
public class PersonDaoImpl implements PersonDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addPerson(Person employee) {
		this.sessionFactory.getCurrentSession().save(employee);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPerson() {
		return this.sessionFactory.getCurrentSession()
				.createQuery("from Person").list();
	}

	@Override
	public void deletePerson(Integer PersonId) {
		Person person = (Person) sessionFactory
				.getCurrentSession().load(Person.class, PersonId);
		if (null != person) {
			this.sessionFactory.getCurrentSession().delete(person);
		}
	}
}
