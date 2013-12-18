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
import com.vaadin.demo.dashboard.domain.Person;
@Theme("dashboard")
@Title("QuickTickets Dashboard")

public class SpringHelloWorld extends UI {

	private static final long serialVersionUID = 1L;
	public static SpringContextHelper helper;

	@Override
	protected void init(VaadinRequest request) {

		helper = new SpringContextHelper(VaadinServlet.getCurrent()
				.getServletContext());
		final VerticalLayout content = new VerticalLayout();
		final HelloWorld bean = (HelloWorld) helper.getBean("HelloWorld");
		content.setMargin(true);
		setContent(content);


		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				content.addComponent(new Label(bean.getMessage()));
				testspring();
			}
		});
		content.addComponent(button);
		bean.execute();
		
	}

	public void testspring() {
		PersonManager personManager = (PersonManager) helper
				.getBean("personManagerImpl");

		Person person = new Person();
		person.setFirstName("johndoe");
		person.setLastName("john doe");
		personManager.addPerson(person);
		System.out.println("User inserted!");
	}
}