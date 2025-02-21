package com.innoveworkshop.bolota;

import com.innoveworkshop.bolota.models.Document;
import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.models.fields.BlankField;
import com.innoveworkshop.bolota.models.fields.DateField;
import com.innoveworkshop.bolota.models.fields.IconField;
import com.innoveworkshop.bolota.models.fields.TextField;
import com.innoveworkshop.bolota.ui.windows.MainWindow;
import com.innoveworkshop.bolota.utils.ResourceManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Program's main runnable class.
 */
public class Main {
	private static final Logger Log = Logger.getLogger(Main.class.getName());

	/**
	 * Application's main entry point.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		final Document document;

		System.out.println("Bolota for Java");
		if (args.length > 0) {
			try {
				document = new Document(new File(args[0]));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			document = getExampleDocument();
		}

		// Implicitly load all our resources.
		ResourceManager.getInstance();

		// Show the application's main window.
		setNativeLookAndFeel();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainWindow window = new MainWindow();
				window.setVisible(true);
				window.getViewer().openDocument(document);
			}
		});
	}

	/**
	 * Sets the platform's native Swing Look and Feel.
	 */
	private static void setNativeLookAndFeel() {
		// Print out the available themes.
		Log.log(Level.INFO, "Available Swing look and feels:");
		UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
		for (UIManager.LookAndFeelInfo look : looks) {
			Log.log(Level.INFO, "    " + look.getName() + ": " + look.getClassName());
		}
		Log.log(Level.INFO, "System look and feel: " + UIManager.getSystemLookAndFeelClassName());

		try {
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Bolota");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			Log.log(Level.WARNING, "Failed to set system look and feel: " + e);
		} catch (ClassNotFoundException e) {
			Log.log(Level.WARNING, "Failed to set system look and feel: " + e);
		} catch (InstantiationException e) {
			Log.log(Level.WARNING, "Failed to set system look and feel: " + e);
		} catch (IllegalAccessException e) {
			Log.log(Level.WARNING, "Failed to set system look and feel: " + e);
		}
	}

	/**
	 * Generates an example Bolota document for testing purposes.
	 *
	 * @return Example bolota document.
	 */
	private static Document getExampleDocument() {
		// Setup document.
		Document doc = new Document();
		doc.setTitle("Example document");
		doc.setSubtitle("Just a simple example of a document.");

		// Add topics to document.
		doc.appendChild(new TextField("First topic"));
		doc.appendChild(new TextField("Second topic"));
		Field field1 = new TextField("Third topic");
		field1.appendChild(new TextField("Sub-item of third topic"));
		field1.appendChild(new IconField(null, ResourceManager.getInstance().fieldIcons.get(10),
				"Example icon"));
		field1.appendChild(new TextField("Another sub-item of third topic"));
		doc.appendChild(field1);
		doc.appendChild(new DateField(null, DateField.getCalendarUTC(), "A sample date topic."));
		doc.appendChild(new BlankField());
		doc.appendChild(new TextField("Fourth topic"));

		return doc;
	}
}
