package com.innoveworkshop.bolota.utils;

import com.innoveworkshop.bolota.models.resources.FieldIcon;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the resources included with the application and their loading in an efficient fashion.
 */
public final class ResourceManager {
	private static ResourceManager INSTANCE;
	public final List<FieldIcon> fieldIcons;

	// Field icon shortcuts.
	private FieldIcon bulletIcon;
	private FieldIcon calendarIcon;

	/**
	 * Constructor for the resource manager singleton object.
	 *
	 * @throws IOException When we are unable to read a resource from archive.
	 */
    private ResourceManager() throws IOException {
		// Get field icons and shortcuts.
	    bulletIcon = new FieldIcon((byte)0, "Bullet", "_Bullet1.png");
		fieldIcons = getFieldIcons();
		for (FieldIcon icon : fieldIcons) {
			if (icon.description.equals("Calendar")) {
				calendarIcon = icon;
			}
		}
    }

	/**
	 * Gets a list of all our field icons.
	 *
	 * @return List of all our field icons preloaded into memory.
	 *
	 * @throws IOException if the icon definition file isn't found or a read error occurs.
	 */
	private ArrayList<FieldIcon> getFieldIcons() throws IOException {
		String line;

		// Load our field icon definitions file.
		BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(
				FieldIcon.ICON_DIR + "/field_icons.tsv")));

		// Read each field icon.
		boolean firstLine = true;
		ArrayList<String> lines = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			// Skip the header line.
			if (firstLine) {
				firstLine = false;
				continue;
			}

			// Store line for later.
			lines.add(line);
		}

		// Create our field icons list.
		ArrayList<FieldIcon> list = new ArrayList<FieldIcon>(lines.size());
		for (String defLine : lines) {
			FieldIcon icon = FieldIcon.fromDefinitionLine(defLine);
			list.add(icon.id - 1, icon);
		}

		return list;
	}

	/**
	 * Gets the global resource manager instance.
	 *
	 * @return Resource manager instance.
	 */
	public static ResourceManager getInstance() {
		// Create instance if we haven't before.
		try {
			if (INSTANCE == null)
				INSTANCE = new ResourceManager();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return INSTANCE;
	}

	/**
	 * Gets a resource as a stream.
	 *
	 * @param resourcePath Path to the resource.
	 *
	 * @return Resource as a stream.
	 *
	 * @throws FileNotFoundException if the resourcePath doesn't point to a valid resource file.
	 */
	public static InputStream getResourceAsStream(String resourcePath) throws FileNotFoundException {
		InputStream in = ResourceManager.class.getClassLoader().getResourceAsStream(resourcePath);
		if (in == null) {
			throw new FileNotFoundException("Could not open resource file \"" + resourcePath +
					"\" as a stream");
		}

		return in;
	}

	/**
	 * Gets a resource URL.
	 *
	 * @param resourcePath Path to the resource.
	 *
	 * @return Resource URL to open and read later.
	 *
	 * @throws FileNotFoundException if the resourcePath doesn't point to a valid resource file.
	 */
	public static URL getResourceURL(String resourcePath) throws FileNotFoundException {
		URL url = ResourceManager.class.getClassLoader().getResource(resourcePath);
		if (url == null) {
			throw new FileNotFoundException("Could not open resource file \"" + resourcePath +
					"\" as a stream");
		}

		return url;
	}

	/**
	 * Gets the regular field bullet icon.
	 *
	 * @return Field bullet icon.
	 */
	public FieldIcon getBulletIcon() {
		return bulletIcon;
	}

	/**
	 * Gets the calendar field icon.
	 *
	 * @return Calendar field icon.
	 */
	public FieldIcon getCalendarIcon() {
		return calendarIcon;
	}
}
