package com.innoveworkshop.bolota.utils;

import java.io.UnsupportedEncodingException;

/**
 * A universal string type that allows for easy conversion to and from UTF-8.
 */
public class UString implements Comparable<UString> {
	private String str;
	private byte[] strUTF8;
	protected boolean synced;

	/**
	 * Initializes the universal string.
	 *
	 * @param str String to initialize the object with.
	 */
	public UString(String str) {
		this.strUTF8 = null;
		set(str);
	}

	/**
	 * Gets the number of characters in the string.
	 *
	 * @return Number of characters in the string.
	 */
	public int getLength() {
		return str.length();
	}

	/**
	 * Gets the number of bytes in the UTF-8 encoded string.
	 *
	 * @return Number of bytes in the UTF-8 string.
	 */
	public int getUTF8Length() {
		return getBytes().length;
	}

	/**
	 * Gets the UTF-8 encoded string in bytes.
	 *
	 * @return UTF-8 encoded string in bytes.
	 */
	public byte[] getBytes() {
		if (synced)
			return strUTF8;

		try {
			strUTF8 = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}

		return strUTF8;
	}

	/**
	 * Sets the string's text.
	 *
	 * @param str New string's text.
	 */
	public void set(String str) {
		this.str = str;
		this.synced = false;
	}

	public int compareTo(UString ustr) {
		return str.compareTo(ustr.toString());
	}

	@Override
	public String toString() {
		return this.str;
	}
}
