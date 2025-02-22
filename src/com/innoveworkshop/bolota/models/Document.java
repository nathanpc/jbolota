package com.innoveworkshop.bolota.models;

import com.innoveworkshop.bolota.exceptions.BolotaFileException;
import com.innoveworkshop.bolota.exceptions.InvalidMagicException;
import com.innoveworkshop.bolota.exceptions.NewerVersionException;
import com.innoveworkshop.bolota.models.fields.DateField;
import com.innoveworkshop.bolota.models.fields.TextField;
import com.innoveworkshop.bolota.utils.UString;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A Bolota document object representation.
 */
public class Document extends Field {
	private final TextField title;
	private final TextField subtitle;
	private final DateField date;

	/**
	 * Length of the document magic number.
	 */
	private static final int DOC_MAGIC_LEN = 3;

	/**
	 * Version of the Bolota document specification.
	 */
	public static final byte DOC_VERSION = 1;

	/**
	 * Constructs a blank document.
	 */
	public Document() {
		super((byte)0, null, new UString("Document Root"));
		title = new TextField(null, "");
		subtitle = new TextField(null, "");
		date = new DateField(null, Calendar.getInstance(), "");
	}

	/**
	 * Constructs a Bolota document object from a file in the filesystem.
	 *
	 * @param file Bolota document file.
	 *
	 * @throws IOException if an error occurred while reading the document.
	 */
	public Document(File file) throws IOException {
		this();
		open(file);
	}

	/**
	 * Opens a Bolota document file and reads it into this object.
	 *
	 * @param file Bolota document file.
	 *
	 * @throws FileNotFoundException if the document file wasn't found.
	 * @throws IOException if an error occurred while reading the document.
	 */
	public void open(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);

		try {
			// Read the header of the document.
			byte[] bytes = new byte[DOC_MAGIC_LEN + 9];
			int len = fis.read(bytes);
			if (len < (DOC_MAGIC_LEN + 9))
				throw new BolotaFileException(file, "Document header ended prematurely");

			// Get read buffer.
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			buffer.order(ByteOrder.LITTLE_ENDIAN);

			// Check if the magic and version are valid.
			if (!((buffer.get() == 'B') && (buffer.get() == 'L') && (buffer.get() == 'T')))
				throw new InvalidMagicException(file);
			byte version = buffer.get();
			if (version > DOC_VERSION)
				throw new NewerVersionException(file, version);

			// Get the length of the file sections.
			int propertiesLength = buffer.getInt();
			int topicsLength = buffer.getInt();

			// Read the properties section.
			bytes = new byte[propertiesLength];
			len = fis.read(bytes);
			if (len < propertiesLength)
				throw new BolotaFileException(file, "Document properties section ended prematurely");
			buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
			buffer.get();  // Ignore field type since we already know it.
			title.fromBytes(buffer);
			buffer.get();  // Ignore field type since we already know it.
			subtitle.fromBytes(buffer);
			buffer.get();  // Ignore field type since we already know it.
			date.fromBytes(buffer);

			// Read the topics section.
			bytes = new byte[topicsLength];
			len = fis.read(bytes);
			if (len < topicsLength)
				throw new BolotaFileException(file, "Document topics section ended prematurely");
			buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);

			// Parse the topics section.
			Field previousTopic = this;
			Field topic;
			while ((topic = Field.createFromBytes(previousTopic, buffer)) != null) {
				previousTopic = topic;
			}

			// Check if we have reached the end of the file.
			if (fis.read() != -1)
				throw new BolotaFileException(file, "Document continues after end of topics section");
		} finally {
			// Close the file stream.
			fis.close();
		}
	}

	/**
	 * Gets the document title.
	 *
	 * @return Document's title.
	 */
	public String getTitle() {
		return this.title.getText();
	}

	/**
	 * Sets the document's title.
	 *
	 * @param title New title of the document.
	 */
	public void setTitle(String title) {
		this.title.setText(title);
	}

	/**
	 * Gets the document's subtitle.
	 *
	 * @return Document's subtitle.
	 */
	public String getSubtitle() {
		return this.subtitle.getText();
	}

	/**
	 * Sets the document's subtitle.
	 *
	 * @param subtitle New subtitle of the document.
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle.setText(subtitle);
	}

	/**
	 * Gets the date and time of the document's creation.
	 *
	 * @return Date and time of the document's creation.
	 */
	public Calendar getDate() {
		return this.date.getDate();
	}

	/**
	 * Sets the date and time of the document's creation.
	 *
	 * @param dt New document's creation date and time.
	 */
	public void setDate(Calendar dt) {
		this.date.setDate(dt);
	}

	/**
	 * Gets the document's topics.
	 *
	 * @return Topics field list.
	 */
	public ArrayList<Field> getTopics() {
		return children;
	}

	@Override
	public boolean isDocumentRoot() {
		return true;
	}

	/**
	 * Always 0 for the document root.
	 *
	 * @return Always 0.
	 */
	@Override
	public byte getDepth() {
		return 0;
	}

	/**
	 * Not implemented.
	 */
	@Override
	public byte fromBytes(ByteBuffer bytes) {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Not implemented.
	 */
	@Override
	public void fromBytes(Field parent, ByteBuffer bytes) {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Not implemented.
	 */
	@Override
	public ByteBuffer getBytes() {
		throw new RuntimeException("Not implemented");
	}

	/**
	 * Not implemented.
	 */
	@Override
	public short getLength() {
		throw new RuntimeException("Not implemented");
	}
}
