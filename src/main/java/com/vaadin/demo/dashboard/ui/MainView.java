package com.vaadin.demo.dashboard.ui;

import com.vaadin.demo.dashboard.data.Generator;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.demo.dashboard.DashboardUI;

@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {
//    CssLayout menu = new CssLayout();
//    CssLayout content = new CssLayout();
    
    public MainView(CssLayout menu,CssLayout content,DashboardUI dashboardui){

        setSizeFull();
        addStyleName("main-view");
         // leftsidebar
        addComponent(Leftsidebar(menu,dashboardui));
        // Content
        addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        setExpandRatio(content, 1);
    }
    
	public VerticalLayout Leftsidebar(CssLayout menu,DashboardUI dashboardui) {
		VerticalLayout sidebar = new VerticalLayout();
		sidebar.addStyleName("sidebar");
		sidebar.setWidth(null);
		sidebar.setHeight("100%");

		// Branding element
		sidebar.addComponent(Branding());

		// Main menu
		sidebar.addComponent(menu);
		sidebar.setExpandRatio(menu, 1);

		// User menu
		sidebar.addComponent(UserMenu(dashboardui));
		
		return sidebar;
	}

	// Branding element
	public CssLayout Branding() {
		CssLayout branding = new CssLayout();
		branding.addStyleName("branding");
		Label logo = new Label("<span>QuickTickets</span> Dashboard",
				ContentMode.HTML);
		logo.setSizeUndefined();
		branding.addComponent(logo);
		return branding;
	}
	// user menu
	public VerticalLayout UserMenu(final DashboardUI dashboardui) {
		VerticalLayout menuobj = new VerticalLayout();

		menuobj.setSizeUndefined();
		menuobj.addStyleName("user");

		Image profilePic = new Image(null, new ThemeResource(
				"img/profile-pic.png"));
		profilePic.setWidth("34px");
		menuobj.addComponent(profilePic);
		Label userName = new Label(Generator.randomFirstName() + " "
				+ Generator.randomLastName());
		userName.setSizeUndefined();
		menuobj.addComponent(userName);

		Command cmd = new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				Notification.show("Not implemented in this demo");
			}
		};
		MenuBar settings = new MenuBar();
		MenuItem settingsMenu = settings.addItem("", null);
		settingsMenu.setStyleName("icon-cog");
		settingsMenu.addItem("Settings", cmd);
		settingsMenu.addItem("Preferences", cmd);
		settingsMenu.addSeparator();
		settingsMenu.addItem("My Account", cmd);
		menuobj.addComponent(settings);

		Button exit = new NativeButton("Exit");
		exit.addStyleName("icon-cancel");
		exit.setDescription("Sign Out");
		menuobj.addComponent(exit);
		exit.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				// buildLoginView(true);
				Notification.show("call buildLoginView");
				dashboardui.buildLoginView(true);
			}
		});
		return menuobj;
	}
}