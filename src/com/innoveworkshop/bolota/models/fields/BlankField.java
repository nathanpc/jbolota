package com.innoveworkshop.bolota.models.fields;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.utils.UString;

import java.nio.ByteBuffer;

/**
 * A Bolota field that is supposed to act as a visual spacer.
 */
public class BlankField extends Field {
	/**
	 * Initializes the blank field.
	 *
	 * @param parent Parent field object.
	 */
	public BlankField(Field parent) {
		super(Field.TYPE_BLANK, parent, null);
	}

	/**
	 * Initializes the blank field.
	 */
	public BlankField() {
		this(null);
	}

	@Override
	public byte fromBytes(ByteBuffer bytes) {
		return super.fromBaseBytes(bytes);
	}

	@Override
	public void fromBytes(Field previous, ByteBuffer bytes) {
		super.fromBaseBytes(previous, bytes);
	}

	@Override
	public ByteBuffer getBytes() {
		return getBaseBytes();
	}

	@Override
	public short getLength() {
		return getBaseLength();
	}

	@Override
	public String getText() {
		return null;
	}
}
