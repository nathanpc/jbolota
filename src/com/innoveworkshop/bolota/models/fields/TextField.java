package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.UString;

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
		super((byte)'T', parent, text);
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
}
