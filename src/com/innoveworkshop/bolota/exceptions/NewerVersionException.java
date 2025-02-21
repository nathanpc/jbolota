package com.innoveworkshop.bolota.exceptions;

import com.innoveworkshop.bolota.models.Document;

import java.io.File;

/**
 * Exception that is thrown whenever a document has a version number that's
 * greater than what we currently support.
 */
public class NewerVersionException extends BolotaFileException {
	/**
	 * Construct the version exception.
	 *
	 * @param file    File that is responsible for the exception.
	 * @param version Version number found in the file.
	 */
	public NewerVersionException(File file, byte version) {
		super(file, "Document version (" + version + ") is newer than what we currently support (" +
			Document.DOC_VERSION + ")");
	}
}
