package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.UString;

import java.util.Date;

/**
 * A Bolota field representing a timestamp.
 */
public class DateField extends Field {
	private Date date;

	/**
	 * Initializes the date field with default values.
	 *
	 * @param parent Parent field object.
	 * @param date   Timestamp represented by the field.
	 * @param text   Text associated with the field.
	 */
	public DateField(Field parent, Date date, UString text) {
		super((byte)'d', parent, text);
		this.date = date;
	}

	/**
	 * Initializes the base field with default values.
	 *
	 * @param parent Parent field object.
	 * @param date   Timestamp represented by the field.
	 * @param text   Text associated with the field.
	 */
	public DateField(Field parent, Date date, String text) {
		this(parent, date, new UString(text));
	}

	/**
	 * Gets the date and time associated with the field.
	 *
	 * @return Date and time associated with the field.
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Sets the date and time associated with the field.
	 *
	 * @param date New date and time to be associated with the field.
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
