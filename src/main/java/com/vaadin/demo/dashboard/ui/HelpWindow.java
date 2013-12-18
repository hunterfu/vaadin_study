package com.vaadin.demo.dashboard.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class HelpWindow extends Window {
	private static final long serialVersionUID = 4250263332401834221L;

	public HelpWindow() {
		super("Workout Log help");

		setWidth("500px");
		setHeight("400px");

		BufferedReader reader;
		StringBuffer contents = new StringBuffer();

		try {
			reader = new BufferedReader(new InputStreamReader(this.getClass()
					.getResourceAsStream("help.html")));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Label help = new Label(contents.toString(), ContentMode.HTML);
		//addComponent(help);
		this.setContent(help);
	}

}
