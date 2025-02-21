package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.UString;

import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * A Bolota field representing a timestamp.
 */
public class DateField extends Field {
	private Calendar date;

	/**
	 * Initializes the date field with default values.
	 *
	 * @param parent Parent field object.
	 * @param date   Timestamp represented by the field.
	 * @param text   Text associated with the field.
	 */
	public DateField(Field parent, Calendar date, UString text) {
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
	public DateField(Field parent, Calendar date, String text) {
		this(parent, date, new UString(text));
	}

	/**
	 * Gets the date and time associated with the field.
	 *
	 * @return Date and time associated with the field.
	 */
	public Calendar getDate() {
		return this.date;
	}

	/**
	 * Sets the date and time associated with the field.
	 *
	 * @param date New date and time to be associated with the field.
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public ByteBuffer getBytes() {
		// Get the base field.
		ByteBuffer bytes =  getBaseBytes();

		// Build up the timestamp structure.
		bytes.putShort((short)date.get(Calendar.YEAR));
		bytes.put((byte)(date.get(Calendar.MONTH) + 1));
		bytes.put((byte)date.get(Calendar.DAY_OF_MONTH));
		bytes.put((byte)date.get(Calendar.HOUR_OF_DAY));
		bytes.put((byte)date.get(Calendar.MINUTE));
		bytes.put((byte)date.get(Calendar.SECOND));
		bytes.put((byte)0);  // Reserved

		return bytes;
	}

	@Override
	public short getLength() {
		return (short)(getBaseLength() + 8);
	}
}
