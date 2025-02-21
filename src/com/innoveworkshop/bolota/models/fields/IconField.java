package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.models.resources.FieldIcon;
import com.innoveworkshop.bolota.utils.UString;

import java.nio.ByteBuffer;

/**
 * A Bolota text field with an associated icon.
 */
public class IconField extends Field {
	private FieldIcon icon;

	/**
	 * Initializes the icon field with default values.
	 *
	 * @param parent Parent field object.
	 * @param icon   Icon associated with the field.
	 * @param text   Text associated with the field.
	 */
	public IconField(Field parent, FieldIcon icon, UString text) {
		super((byte)'I', parent, text);
		this.icon = icon;
	}

	/**
	 * Initializes the icon field with default values.
	 *
	 * @param parent Parent field object.
	 * @param icon   Icon associated with the field.
	 * @param text   Text associated with the field.
	 */
	public IconField(Field parent, FieldIcon icon, String text) {
		this(parent, icon, new UString(text));
	}

	/**
	 * Gets the associated field icon.
	 *
	 * @return Associated field icon.
	 */
	public FieldIcon getIcon() {
		return icon;
	}

	/**
	 * Sets a new field icon.
	 *
	 * @param icon New icon to be associated with this field.
	 */
	public void setIcon(FieldIcon icon) {
		this.icon = icon;
	}

	@Override
	public ByteBuffer getBytes() {
		// Get the base field and the icon ID.
		ByteBuffer bytes =  getBaseBytes();
		bytes.put(icon.id);

		return bytes;
	}

	@Override
	public short getLength() {
		return (short)(getBaseLength() + 1);
	}
}
