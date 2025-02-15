package com.innoveworkshop.bolota.ui.windows;

import com.innoveworkshop.bolota.models.Document;
import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.models.fields.DateField;
import com.innoveworkshop.bolota.models.fields.TextField;
import com.innoveworkshop.bolota.ui.components.DocumentViewer;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Our application's main window.
 */
public class MainWindow extends JFrame {
	private DocumentViewer viewer = null;

	/**
	 * Creates the main window of our application.
	 */
	public MainWindow() {
		super();
		setupComponents();
	}

	/**
	 * Sets up the components and lays them out in our frame.
	 */
	private void setupComponents() {
		// Top-level layout manager for the frame.
		BorderLayout topLayout = new BorderLayout();
		setLayout(topLayout);

		// Main document viewer.
		viewer = new DocumentViewer();
		viewer.setPreferredSize(new Dimension(600, 800));
		add(viewer, BorderLayout.CENTER);

		// Sets the properties for the frame itself.
		setSize(600, 800);
		setJMenuBar(setupMenuBar());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}

	/**
	 * Sets up the menu bar of the frame.
	 */
	private JMenuBar setupMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu menu = null;
		JMenuItem item = null;

		// File menu.
		menu = new JMenu("File");
		item = new JMenuItem("New...");
		menu.add(item);
		item = new JMenuItem("Open...");
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Save...");
		menu.add(item);
		item = new JMenuItem("Save As...");
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Properties...");
		menu.add(item);
		mb.add(menu);

		// Field menu.
		menu = new JMenu("Field");
		item = new JMenuItem("Edit...");
		menu.add(item);
		item = new JMenuItem("Delete...");
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Prepend Field...");
		menu.add(item);
		item = new JMenuItem("Append Field...");
		menu.add(item);
		item = new JMenuItem("Create Child Field...");
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Move Up");
		menu.add(item);
		item = new JMenuItem("Move Down");
		menu.add(item);
		menu.addSeparator();
		item = new JMenuItem("Indent");
		menu.add(item);
		item = new JMenuItem("De-indent");
		menu.add(item);
		mb.add(menu);

		// Help menu.
		menu = new JMenu("Help");
		item = new JMenuItem("Cheatsheet...");
		menu.add(item);
		item = new JMenuItem("About...");
		menu.add(item);
		mb.add(menu);

		return mb;
	}

	/**
	 * Gets the document viewer in the main window.
	 *
	 * @return Document viewer component.
	 */
	public DocumentViewer getViewer() {
		return viewer;
	}
}
