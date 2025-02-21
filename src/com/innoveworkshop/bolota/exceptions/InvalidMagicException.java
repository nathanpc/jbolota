package com.innoveworkshop.bolota.exceptions;

import java.io.File;

/**
 * Exception thrown whenever an invalid file magic is read from a supposedly
 * Bolota document file.
 */
public class InvalidMagicException extends BolotaFileException {
	/**
	 * Constructs the invalid magic exception.
	 *
	 * @param file File that is responsible for the exception.
	 */
	public InvalidMagicException(File file) {
		super(file, "An invalid magic was found");
	}
}
