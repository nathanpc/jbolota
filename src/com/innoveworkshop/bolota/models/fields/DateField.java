package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.ResourceManager;
import com.innoveworkshop.bolota.utils.UString;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

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
		super(Field.TYPE_DATE, parent, text);
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
	 * Gets a {@link Calendar} instance with the UTC timezone.
	 *
	 * @return Calendar instance in UTC.
	 */
	public static Calendar getCalendarUTC() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
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
	public byte fromBytes(ByteBuffer bytes) {
		// Read field base.
		byte depth = super.fromBaseBytes(bytes);

		// Read in the individual parts of the timestamp.
		short year = bytes.getShort();
		byte month = (byte)(bytes.get() - 1);
		byte day = bytes.get();
		byte hour = bytes.get();
		byte minute = bytes.get();
		byte second = bytes.get();
		bytes.get();  // Reserved.

		// Set up our internal timestamp object.
		date = getCalendarUTC();
		date.set(year, month, day, hour, minute, second);

		return depth;
	}

	@Override
	public void fromBytes(Field parent, ByteBuffer bytes) {
		super.fromBaseBytes(parent, bytes);
	}

	@Override
	public void copy(Field field) {
		super.copy(field);
		if (field instanceof DateField)
			date = ((DateField)field).date;
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
