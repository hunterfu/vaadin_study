package com.vaadin.demo.dashboard.ui;

import java.util.HashMap;
import java.util.Iterator;

import com.vaadin.demo.dashboard.DashboardUI;
import com.vaadin.demo.dashboard.HelpManager;
import com.vaadin.demo.dashboard.data.DataProvider;
import com.vaadin.demo.dashboard.data.Generator;
import com.vaadin.event.Transferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {
//    CssLayout menu = new CssLayout();
//    CssLayout content = new CssLayout();
	private Transferable items;
	boolean autoCreateReport = false;
	public DataProvider dataProvider;
	public DashboardUI UI;
	public HelpManager helpManager;
	Table transactions;
	// 导航信息
	HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
		{
			put("/dashboard", DashboardView.class);
			put("/sales", SalesView.class);
			put("/transactions", TransactionsView.class);
			put("/reports", ReportsView.class);
			put("/schedule", ScheduleView.class);
			put("/attention", AddressBookView.class);
		}
	};

	HashMap<String, Button> viewNameToMenuButton = new HashMap<String, Button>();

	private Navigator nav;
   
   public MainView(CssLayout menu,CssLayout content,DashboardUI dashboardui){
        setSizeFull();
        addStyleName("main-view");
        //init val
        UI = dashboardui;
        dataProvider = UI.dataProvider;
        helpManager = UI.helpManager;
         // leftsidebar
        addComponent(Leftsidebar(menu,dashboardui));
        // Content
        addComponent(content);
        content.setSizeFull();
        content.addStyleName("view-content");
        setExpandRatio(content, 1);
        // init url route
        seturlRoute(dashboardui,content);
        // nav bar
        navmenu(menu,dashboardui);
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
	
	public void navmenu(final CssLayout menu,final DashboardUI dashboardui){
		
		menu.removeAllComponents();

		for (final String view : new String[] { "dashboard", "sales",
				"transactions", "reports", "schedule","attention" }) {
			Button b = new NativeButton(view.substring(0, 1).toUpperCase()
					+ view.substring(1).replace('-', ' '));
			b.addStyleName("icon-" + view);
			b.addClickListener(new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					clearMenuSelection(menu);
					event.getButton().addStyleName("selected");
					if (!nav.getState().equals("/" + view))
						nav.navigateTo("/" + view);
				}
			});
			if (view.equals("reports")) {
				// Add drop target to reports button
				DragAndDropWrapper reports = new DragAndDropWrapper(b);
				reports.setDragStartMode(DragStartMode.NONE);
				reports.setDropHandler(new DropHandler() {

					@Override
					public void drop(DragAndDropEvent event) {
						clearMenuSelection(menu);
						viewNameToMenuButton.get("/reports").addStyleName(
								"selected");
						autoCreateReport = true;
						items = event.getTransferable();
						nav.navigateTo("/reports");
					}

					@Override
					public AcceptCriterion getAcceptCriterion() {
						return AcceptItem.ALL;
					}

				});
				menu.addComponent(reports);
				menu.addStyleName("no-vertical-drag-hints");
				menu.addStyleName("no-horizontal-drag-hints");
			} else {
				menu.addComponent(b);
			}

			viewNameToMenuButton.put("/" + view, b);
		}
		menu.addStyleName("menu");
		menu.setHeight("100%");

		viewNameToMenuButton.get("/dashboard").setHtmlContentAllowed(true);
		viewNameToMenuButton.get("/dashboard").setCaption(
				"Dashboard<span class=\"badge\">2</span>");

		String f = Page.getCurrent().getUriFragment();
		if (f != null && f.startsWith("!")) {
			f = f.substring(1);
		}
		if (f == null || f.equals("") || f.equals("/")) {
			nav.navigateTo("/dashboard");
			menu.getComponent(0).addStyleName("selected");
			dashboardui.helpManager.showHelpFor(DashboardView.class);
		} else {
			nav.navigateTo(f);
			dashboardui.helpManager.showHelpFor(routes.get(f));
			viewNameToMenuButton.get(f).addStyleName("selected");
		}

		nav.addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {
				dashboardui.helpManager.closeAll();
				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {
				View newView = event.getNewView();
				dashboardui.helpManager.showHelpFor(newView);
				if (autoCreateReport && newView instanceof ReportsView) {
					((ReportsView) newView).autoCreate(2, items, transactions);
				}
				autoCreateReport = false;
			}
		});

	}
		
	private void clearMenuSelection(CssLayout menu) {
			//for (Iterator<Component> it = menu.getComponentIterator(); it.hasNext();) {
		for (Iterator<Component> it = menu.iterator(); it.hasNext();) {
				Component next = it.next();
				if (next instanceof NativeButton) {
					next.removeStyleName("selected");
				} else if (next instanceof DragAndDropWrapper) {
					// Wow, this is ugly (even uglier than the rest of the code)
					((DragAndDropWrapper) next).iterator().next()
							.removeStyleName("selected");
				}
			}
		}
	void updateReportsButtonBadge(String badgeCount) {
		viewNameToMenuButton.get("/reports").setHtmlContentAllowed(true);
		viewNameToMenuButton.get("/reports").setCaption(
				"Reports<span class=\"badge\">" + badgeCount + "</span>");
	}

	public void clearDashboardButtonBadge() {
		viewNameToMenuButton.get("/dashboard").setCaption("Dashboard");
	}

	//boolean autoCreateReport = false;
	//Table transactions;

	public void openReports(Table t) {
		transactions = t;
		autoCreateReport = true;
		nav.navigateTo("/reports");
		//clearMenuSelection();
		viewNameToMenuButton.get("/reports").addStyleName("selected");
	}

	public void seturlRoute(final DashboardUI dashboardui,CssLayout content){
		nav = new Navigator(dashboardui, content);

		for (String route : routes.keySet()) {
			nav.addView(route, routes.get(route));
		}

	}
}