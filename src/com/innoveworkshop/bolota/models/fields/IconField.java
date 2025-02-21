package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.models.resources.FieldIcon;
import com.innoveworkshop.bolota.utils.ResourceManager;
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
		super(Field.TYPE_ICON, parent, text);
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
	public byte fromBytes(ByteBuffer bytes) {
		byte depth = super.fromBaseBytes(bytes);
		icon = ResourceManager.getInstance().fieldIcons.get(bytes.get());

		return depth;
	}

	@Override
	public void fromBytes(Field parent, ByteBuffer bytes) {
		super.fromBaseBytes(parent, bytes);
	}

	@Override
	public void copy(Field field) {
		super.copy(field);
		if (field instanceof IconField)
			icon = ((IconField)field).icon;
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
