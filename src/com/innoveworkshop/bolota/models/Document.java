package com.innoveworkshop.bolota.models;

import com.innoveworkshop.bolota.models.fields.DateField;
import com.innoveworkshop.bolota.models.fields.TextField;
import com.innoveworkshop.bolota.utils.UString;

import java.util.ArrayList;
import java.util.Date;

/**
 * A Bolota document object representation.
 */
public class Document extends Field {
	private final TextField title;
	private final TextField subtitle;
	private final DateField date;

	/**
	 * Constructs a blank document.
	 */
	public Document() {
		super((byte)0, null, new UString("Document Root"));
		title = new TextField(null, "");
		subtitle = new TextField(null, "");
		date = new DateField(null, new Date(), "");
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
	public Date getDate() {
		return this.date.getDate();
	}

	/**
	 * Sets the date and time of the document's creation.
	 *
	 * @param dt New document's creation date and time.
	 */
	public void setDate(Date dt) {
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
}
