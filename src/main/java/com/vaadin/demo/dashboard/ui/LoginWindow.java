package com.vaadin.demo.dashboard.ui;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.demo.dashboard.DashboardUI;

@SuppressWarnings("serial")
public class LoginWindow extends VerticalLayout {

	public LoginWindow(final DashboardUI dashboradui) {
		setSizeFull();
		addStyleName("login-layout");
		
		final CssLayout loginPanel = new CssLayout();
		loginPanel.addStyleName("login-panel");

		HorizontalLayout labels = new HorizontalLayout();
		labels.setWidth("100%");
		labels.setMargin(true);
		labels.addStyleName("labels");
		loginPanel.addComponent(labels);

		Label welcome = new Label("Welcome");
		welcome.setSizeUndefined();
		welcome.addStyleName("h4");
		labels.addComponent(welcome);
		labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

		Label title = new Label("Dashboard");
		title.setSizeUndefined();
		title.addStyleName("h2");
		title.addStyleName("light");
		labels.addComponent(title);
		labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

		HorizontalLayout fields = new HorizontalLayout();
		fields.setSpacing(true);
		fields.setMargin(true);
		fields.addStyleName("fields");

		final TextField username = new TextField("Username");
		username.focus();
		fields.addComponent(username);

		final PasswordField password = new PasswordField("Password");
		fields.addComponent(password);

		final Button signin = new Button("Sign In");
		signin.addStyleName("default");
		fields.addComponent(signin);
		fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

		
		final ShortcutListener enter = new ShortcutListener("Sign In",
				KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				signin.click();
			}
		};
		signin.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if (username.getValue() != null
						&& username.getValue().equals("")
						&& password.getValue() != null
						&& password.getValue().equals("")) {
					signin.removeShortcutListener(enter);
					//buildMainView();
					Notification.show("call buildMainView()");
					dashboradui.buildMainView();
				} else {
					if (loginPanel.getComponentCount() > 2) {
						// Remove the previous error message
						loginPanel.removeComponent(loginPanel.getComponent(2));
					}
					// Add new error message
					Label error = new Label(
							"Wrong username or password. <span>Hint: try empty values</span>",
							ContentMode.HTML);
					error.addStyleName("error");
					error.setSizeUndefined();
					error.addStyleName("light");
					// Add animation
					error.addStyleName("v-animate-reveal");
					loginPanel.addComponent(error);
					username.focus();
				}
			}
		});

		signin.addShortcutListener(enter);

		loginPanel.addComponent(fields);

		addComponent(loginPanel);
		setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);
	}
}