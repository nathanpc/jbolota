package com.innoveworkshop.bolota.models.resources;

import com.innoveworkshop.bolota.utils.ResourceManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * A field icon from an icon type field topic.
 */
public class FieldIcon extends ImageIcon {
	public final String description;
	public final byte id;

	/**
	 * Directory in the resources folder where the field icons are located.
	 */
	public static final String ICON_DIR = "icons/fields";

	/**
	 * Constructs and reads a field icon image.
	 *
	 * @param id          ID number of the field icon.
	 * @param description Description to be shown in the UI.
	 * @param filename    Filename to read the image icon from.
	 *
	 * @throws IOException if an error occurs while trying to read the image.
	 */
	public FieldIcon(byte id, String description, String filename) throws IOException {
		// Populate the basics.
		super();
		this.description = description;
		this.id = id;

		// Get resource path and read the image into ourselves.
		URL resourcePath = ResourceManager.getResourceURL(ICON_DIR + "/" + filename);
		setImage(ImageIO.read(resourcePath));
	}

	/**
	 * Constructs a field icon object from a line in the definitions file.
	 *
	 * @param line Field icon definitions file line.
	 *
	 * @return Field icon object.
	 *
	 * @throws IOException if an error occurs while trying to read the image.
	 */
	public static FieldIcon fromDefinitionLine(String line) throws IOException {
		String[] parts = line.split("\t");
		return new FieldIcon(Byte.parseByte(parts[0]), parts[1], parts[2]);
	}
}
