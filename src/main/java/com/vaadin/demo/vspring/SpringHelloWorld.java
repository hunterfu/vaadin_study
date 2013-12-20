package com.vaadin.demo.vspring;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.demo.vspring.serviceintf.PersonManager;
import com.vaadin.demo.dashboard.domain.Department;
import com.vaadin.demo.dashboard.domain.Person;
import com.vaadin.demo.vspring.serviceintf.PersonManager;
import com.vaadin.demo.vspring.serviceimpl.PersonManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Theme("dashboard")
@Title("QuickTickets Dashboard")
//@Configurable
public class SpringHelloWorld extends UI {

	private static final long serialVersionUID = 1L;
	public static SpringContextHelper helper;
	@Autowired
	private  PersonManager personManager;
	@Autowired
	HelloWorld bean;
	//PersonManagerImpl personManager;
	@Override
	protected void init(VaadinRequest request) {

		helper = new SpringContextHelper(VaadinServlet.getCurrent()
				.getServletContext());
		final VerticalLayout content = new VerticalLayout();
		//final HelloWorld bean = (HelloWorld) helper.getBean("HelloWorld");
		helper.Inject(this);
		content.setMargin(true);
		setContent(content);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				content.addComponent(new Label(bean.getMessage()));
				testspring();
				//createDemoData();
			}
		});
		content.addComponent(button);
		bean.execute();
		

	}

	public void createDemoData() {
//		PersonManager personManager = (PersonManager) helper
//				.getBean("personManagerImpl");
		String[] groupsNames = { "Corporate Development", "Human Resources",
				"Legal", "Environment", "Quality Assurance",
				"Research and Development", "Production", "Sales", "Marketing" };
		String[] officeNames = { "London", "New York", "Tokyo", "Turku" };
		String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };
		String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel",
				"Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
				"Barks", "Ross", "Schneider", "Tate" };
		String cities[] = { "Amsterdam", "Berlin", "Helsinki", "Hong Kong",
				"London", "Luxemburg", "New York", "Oslo", "Paris", "Rome",
				"Stockholm", "Tokyo", "Turku" };
		String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave",
				"279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
				"6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
				"161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
				"448-8295 Mi Avenue", "6419 Non Av.",
				"659-2538 Elementum Street", "2205 Quis St.",
				"252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
				"3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.",
				"2873 Nonummy Av.", "7342 Mi, Avenue",
				"539-3914 Dignissim. Rd.", "539-3675 Magna Avenue",
				"Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
				"141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.",
				"6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.",
				"2870 Vestibulum St.", "Ap #722 Aenean Avenue",
				"446-968 Augue Ave", "1141 Ultricies Street",
				"Ap #992-5769 Nunc Street", "6690 Porttitor Avenue",
				"Ap #105-1700 Risus Street",
				"P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
				"414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
				"561-9262 Iaculis Avenue" };

		Random r = new Random();

		int amount = r.nextInt(10) + 1;
		System.out.println("\namount =" + amount );
		for (int i = 0; i < amount; i++) {
			Person p = new Person();
			p.setFirstName(fnames[r.nextInt(fnames.length)]);
			p.setLastName(lnames[r.nextInt(lnames.length)]);
			p.setCity(cities[r.nextInt(cities.length)]);
			p.setPhoneNumber("+358 02 555 " + r.nextInt(10) + r.nextInt(10)
					+ r.nextInt(10) + r.nextInt(10));
			int n = r.nextInt(100000);
			if (n < 10000) {
				n += 10000;
			}
			p.setZipCode("" + n);
			p.setStreet(streets[r.nextInt(streets.length)]);

			personManager.addPerson(p);

		}
	}

	public void testspring() {
//		PersonManager personManager = (PersonManager) helper
//				.getBean("personManagerImpl");

		Person person = new Person();
		person.setFirstName("johndoe");
		person.setLastName("john doe");
		personManager.addPerson(person);
		System.out.println("User inserted!");
		List<Person> personall = personManager.getAllPerson();

		System.out.println("\nUser list fetched!" + "\nUser count: "
				+ personall.size());
	}
}