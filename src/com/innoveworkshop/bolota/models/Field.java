package com.innoveworkshop.bolota.models;

import com.innoveworkshop.bolota.utils.UString;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
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
