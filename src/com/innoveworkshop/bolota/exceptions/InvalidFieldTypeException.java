package com.innoveworkshop.bolota.exceptions;

import java.io.IOException;

/**
 * Exception that's thrown whenever an invalid field type character is found
 * when reading a document.
 */
public class InvalidFieldTypeException extends IOException {
	/**
	 * Constructs the exception based on the type found.
	 *
	 * @param type Type character found.
	 */
	public InvalidFieldTypeException(byte type) {
		super("Invalid field type " + type + " encountered when reading field");
	}
}
