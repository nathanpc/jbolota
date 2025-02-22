package com.innoveworkshop.bolota.models;

import com.innoveworkshop.bolota.exceptions.InvalidFieldTypeException;
import com.innoveworkshop.bolota.models.fields.BlankField;
import com.innoveworkshop.bolota.models.fields.DateField;
import com.innoveworkshop.bolota.models.fields.IconField;
import com.innoveworkshop.bolota.models.fields.TextField;
import com.innoveworkshop.bolota.utils.UString;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * The base of every Bolota field.
 */
public abstract class Field implements MutableTreeNode {
	private final byte type;
	private final UString text;
	private Field parent;
	protected final ArrayList<Field> children;

	/**
	 * Bolota field type character for text.
	 */
	public static final byte TYPE_TEXT = 'T';

	/**
	 * Bolota field type character for timestamp.
	 */
	public static final byte TYPE_DATE = 'd';

	/**
	 * Bolota field type character for icon.
	 */
	public static final byte TYPE_ICON = 'I';

	/**
	 * Bolota field type character for blank.
	 */
	public static final byte TYPE_BLANK = '0';

	/**
	 * Initializes the base field with default values.
	 *
	 * @param type   Field type identifier character.
	 * @param parent Parent field object.
	 * @param text   Default text associated with the field.
	 */
	protected Field(byte type, Field parent, UString text) {
		this.type = type;
		this.text = text;
		this.parent = parent;
		this.children = new ArrayList<Field>();
	}

	/**
	 * Constructs a brand new base field with the values from another one.
	 *
	 * @param field Reference field to be copied over.
	 */
	public Field(Field field) {
		this(field.type, field.parent, new UString(""));
		copy(field);
	}

	/**
	 * Populates the field object based on the {@link ByteBuffer} that was read from a Bolota
	 * document file.
	 *
	 * @param bytes Buffer read from a document that contains a single field entry.
	 */
	public abstract byte fromBytes(ByteBuffer bytes);

	/**
	 * Populates the field object based on the {@link ByteBuffer} that was read from a Bolota
	 * document file and appends it automatically to parent field that's determined by the
	 * depth read.
	 *
	 * <p></p>
	 *
	 * <p><b style="color: red;">This method will append the field automatically to the right
	 * parent field.</b></p>
	 *
	 * @param previous Previous field that was added to the document.
	 * @param bytes    Buffer read from a document that contains a single field entry.
	 */
	public abstract void fromBytes(Field previous, ByteBuffer bytes);

	/**
	 * Populates the field object base from a {@link ByteBuffer} that was read from a Bolota
	 * document file.
	 *
	 * @param bytes Buffer read from a document that contains a single field entry.
	 *
	 * @return Depth of the field as read from the byte buffer.
	 */
	protected byte fromBaseBytes(ByteBuffer bytes) {
		// Read field base.
		byte depth = bytes.get();
		short fieldLength = bytes.getShort();
		short textLength = bytes.getShort();
		if (textLength > 0) {
			byte[] unicodeString = new byte[textLength];
			bytes.get(unicodeString);
			if (text != null)
				text.set(unicodeString);
		}

		return depth;
	}

	/**
	 * Populates the field object base from a {@link ByteBuffer} that was read from a Bolota
	 * document file and appends it automatically to parent field that's determined by the
	 * depth read.
	 *
	 * <p></p>
	 *
	 * <p><b style="color: red;">This method will append the field automatically to the right
	 * parent field.</b></p>
	 *
	 * @param previous Previous field that was added to the document.
	 * @param bytes    Buffer read from a document that contains a single field entry.
	 */
	protected void fromBaseBytes(Field previous, ByteBuffer bytes) {
		// Read field base.
		byte depth = fromBytes(bytes);

		// Get the base parent.
		Field parent = previous;
		if (!parent.isDocumentRoot())
			parent = previous.getParent();

		// Select the right parent based on depth.
		if (depth < previous.getDepth()) {
			// Next topic of a parent field.
			while (depth != previous.getDepth())
				previous = previous.getParent();
			parent = previous.getParent();
		} else if (depth > previous.getDepth()) {
			// Child of the last field.
			if ((depth - previous.getDepth()) > 1)
				throw new RuntimeException("Field depth forward jump greater than 1");
			parent = previous;
		}

		// Append this field to its rightful parent.
		parent.appendChild(this);
	}

	/**
	 * Gets a proper field object based on the {@link ByteBuffer} that was read from a Bolota
	 * document file.
	 *
	 * @param previous Previous field that was added to the document.
	 * @param bytes    Buffer read from a document that contains a single field entry.
	 *
	 * @return Parsed field object of the correct type for the read field or {@code null} if
	 *         we have reached the end of the buffer.
	 *
	 * @throws InvalidFieldTypeException whenever an invalid field type is found when reading.
	 */
	protected static Field createFromBytes(Field previous, ByteBuffer bytes) throws InvalidFieldTypeException {
		Field field = null;

		// Check if we have reached the end of the buffer.
		if (!bytes.hasRemaining())
			return null;

		// Create field based on type.
		byte type = bytes.get();
		switch (type) {
			case TYPE_TEXT:
				field = new TextField(null, new UString(""));
				break;
			case TYPE_DATE:
				field = new DateField(null, null, new UString(""));
				break;
			case TYPE_ICON:
				field = new IconField(null, null, new UString(""));
				break;
			case TYPE_BLANK:
				field = new BlankField(null);
				break;
			default:
				throw new InvalidFieldTypeException(type);
		}

		// Populate the field.
		field.fromBytes(previous, bytes);

		return field;
	}

	/**
	 * Copies internal values from another field object into ours.
	 *
	 * @param field Reference field to be copied over.
	 */
	public void copy(Field field) {
		text.set(field.text.toString());
		parent = field.parent;
		children.clear();
		children.addAll(field.children);
	}

	/**
	 * Gets the bytes relative to this field as they should be written to a file.
	 *
	 * @return Binary representation of the object as needed for writing to a file.
	 */
	public abstract ByteBuffer getBytes();

	/**
	 * Gets the bytes relative to the base of this field as they should be written to a file.
	 *
	 * @return Binary representation of the base object as needed for writing to a file.
	 */
	public ByteBuffer getBaseBytes() {
		// Allocate our LE bytes buffer.
		ByteBuffer bytes = ByteBuffer.allocate(getLength());
		bytes.order(ByteOrder.LITTLE_ENDIAN);

		// Put the data in the buffer.
		bytes.put(type);
		bytes.put(getDepth());
		bytes.putShort(getLength());
		if (text != null) {
			bytes.putShort((short)text.getUTF8Length());
			bytes.put(text.getBytes());
		} else {
			bytes.putShort((short)0);
		}

		return bytes;
	}

	/**
	 * Gets the entire length of the field in its binary form.
	 *
	 * @return Length of the field in bytes including its header and data.
	 */
	public abstract short getLength();

	/**
	 * Gets the length of the base of the field in its binary form.
	 *
	 * @return Length of the base field in bytes including its header and data.
	 */
	public short getBaseLength() {
		// Type (U8) + Depth (U8) + Length (U16) + Text Length (U16) = 6.
		return (short)(6 + text.getUTF8Length());
	}

	/**
	 * Gets the depth of this node in the tree.
	 *
	 * @return Depth of the node.
	 */
	public byte getDepth() {
		byte count = -1;

		Field field = this;
		while (field.parent != null) {
			count++;
			field = field.parent;
		}

		return count;
	}

	/**
	 * Appends a child field to this field.
	 *
	 * @param field New child field to be appended.
	 */
	public void appendChild(Field field) {
		field.parent = this;
		children.add(field);
	}

	/**
	 * Gets the field type identifier character.
	 *
	 * @return Field type identifier character.
	 */
	public byte getType() {
		return this.type;
	}

	/**
	 * Gets the field's text component.
	 *
	 * @return Default text field value.
	 */
	public String getText() {
		return this.text.toString();
	}

	/**
	 * Sets the field's default text.
	 *
	 * @param text New text to be associated with the field.
	 */
	public void setText(String text) {
		this.text.set(text);
	}

	/**
	 * Gets the last child of the field.
	 *
	 * @return Last child of the field.
	 */
	public Field getLastChild() {
		return getChildAt(getChildCount() - 1);
	}

	/**
	 * Checks if the field is actually the document root and requires special treatment.
	 *
	 * @return {@code true} if the field is actually the document root object.
	 */
	public boolean isDocumentRoot() {
		return false;
	}

	///
	/// TreeNode implementation
	///

	public Field getChildAt(int childIndex) {
		return children.get(childIndex);
	}

	public int getChildCount() {
		return children.size();
	}

	public Field getParent() {
		return parent;
	}

	public int getIndex(TreeNode node) {
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) == node)
				return i;
		}

		return -1;
	}

	public boolean getAllowsChildren() {
		return true;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public Enumeration<Field> children() {
		return Collections.enumeration(children);
	}

	///
	/// MutableTreeNode implementation
	///

	public void insert(MutableTreeNode child, int index) {
		if (!(child instanceof Field))
			throw new RuntimeException("Tried inserting a child field that is not of type Field");

		Field field = (Field)child;
		field.parent = this;
		children.add(index, field);
	}

	public void remove(int index) {
		Field field = children.remove(index);
		field.parent = null;
	}

	public void remove(MutableTreeNode node) {
		if (!(node instanceof Field))
			throw new RuntimeException("Tried removing a child field that is not of type Field");

		for (int i = 0; i < children.size(); i++) {
			Field field = children.get(i);
			if (field == node) {
				field.remove(i);
				return;
			}
		}
	}

	public void setUserObject(Object object) {
		throw new RuntimeException("Cannot set user object on a Field object");
	}

	public void removeFromParent() {
		parent.remove(this);
		parent = null;
	}

	public void setParent(MutableTreeNode newParent) {
		if (parent == newParent)
			return;
		if (!(newParent instanceof Field))
			throw new RuntimeException("Tried setting a parent field that is not of type Field");

		((Field)newParent).appendChild(this);
	}
}
