/**
 * DISCLAIMER
 * 
 * The quality of the code is such that you should not copy any of it as best
 * practice how to build Vaadin applications.
 * 
 * @author jouni@vaadin.com
 * 
 */

package com.vaadin.demo.dashboard;

import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.demo.dashboard.data.DataProvider;
import com.vaadin.demo.dashboard.data.MyConverterFactory;
import com.vaadin.demo.dashboard.ui.LoginWindow;
import com.vaadin.demo.dashboard.ui.MainView;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.demo.dashboard.ui.DemoDataGenerator;
@Theme("dashboard")
@Title("QuickTickets Dashboard")
public class DashboardUI extends UI {

	public DataProvider dataProvider = new DataProvider();
	public static final String PERSISTENCE_UNIT = "addressbook";
	private static final long serialVersionUID = 1L;

	CssLayout root = new CssLayout();
	CssLayout menu = new CssLayout();
	CssLayout content = new CssLayout();
	// LoginWindow loginLayout;
	LoginWindow loginLayout;
	MainView mainview;
	public HelpManager helpManager;

	@Override
	protected void init(VaadinRequest request) {
		getSession().setConverterFactory(new MyConverterFactory());
		//DemoDataGenerator.create();
		helpManager = new HelpManager(this);

		setLocale(Locale.US);

		setContent(root);
		root.addStyleName("root");
		root.setSizeFull();

		// Unfortunate to use an actual widget here, but since CSS generated
		// elements can't be transitioned yet, we must
		Label bg = new Label();
		bg.setSizeUndefined();
		bg.addStyleName("login-bg");
		root.addComponent(bg);

		buildLoginView(false);

	}

	public void buildLoginView(boolean exit) {
		if (exit) {
			root.removeAllComponents();
		}
		helpManager.closeAll();
		HelpOverlay w = helpManager
				.addOverlay(
						"Welcome to the Dashboard Demo Application",
						"<p>This application is not real, it only demonstrates an application built with the <a href=\"http://vaadin.com\">Vaadin framework</a>.</p><p>No username or password is required, just click the ‘Sign In’ button to continue. You can try out a random username and password, though.</p>",
						"login");
		w.center();
		addWindow(w);

		addStyleName("login");

		if (loginLayout == null) {
			loginLayout = new LoginWindow(this);
		}
		root.addComponent(loginLayout);

	}

	public void buildMainView() {

		helpManager.closeAll();
		removeStyleName("login");
		root.removeComponent(loginLayout);
		if (mainview == null) {
			mainview = new MainView(menu, content, this);
		}
		root.addComponent(mainview);

	}

	HelpManager getHelpManager() {
		return helpManager;
	}

}
