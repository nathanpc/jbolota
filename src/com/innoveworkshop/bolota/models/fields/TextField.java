package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.exceptions.InvalidFieldTypeException;
import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.UString;

import java.nio.ByteBuffer;

/**
 * The simplest Bolota text-type field.
 */
public class TextField extends Field {
	/**
	 * Initializes a simple text field.
	 *
	 * @param parent Parent field object.
	 * @param text   Text associated with the field.
	 */
	public TextField(Field parent, UString text) {
		super(Field.TYPE_TEXT, parent, text);
	}

	/**
	 * Initializes a simple text field.
	 *
	 * @param parent Parent field object.
	 * @param text   Text associated with the field.
	 */
	public TextField(Field parent, String text) {
		this(parent, new UString(text));
	}

	/**
	 * Initializes a simple text field.
	 *
	 * @param text Text associated with the field.
	 */
	public TextField(String text) {
		this(null, text);
	}

	@Override
	public byte fromBytes(ByteBuffer bytes) {
		return super.fromBaseBytes(bytes);
	}

	@Override
	public void fromBytes(Field parent, ByteBuffer bytes) {
		super.fromBaseBytes(parent, bytes);
	}

	@Override
	public ByteBuffer getBytes() {
		return getBaseBytes();
	}

	@Override
	public short getLength() {
		return getBaseLength();
	}
}
