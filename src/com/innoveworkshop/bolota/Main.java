package com.innoveworkshop.bolota;

import com.innoveworkshop.bolota.ui.windows.MainWindow;

import javax.swing.*;
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
		System.out.println("Bolota!");

		// Show the application's main window.
		setNativeLookAndFeel();
		MainWindow window = new MainWindow();
		window.setVisible(true);
	}

	/**
	 * Sets the platform's native Swing Look and Feel.
	 */
	private static void setNativeLookAndFeel() {
		try {
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

}
