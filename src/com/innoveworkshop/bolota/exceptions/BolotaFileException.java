package com.innoveworkshop.bolota.exceptions;

import java.io.File;
import java.io.IOException;

/**
 * A generic exception associated with a Bolota document file.
 */
public class BolotaFileException extends IOException {
	private final File file;

	/**
	 * Constructs a generic Bolota document exception with a specified reason.
	 *
	 * @param file   File that is responsible for the exception.
	 * @param reason Reason for the exception.
	 */
	public BolotaFileException(File file, String reason) {
		super(buildReason(reason, file));
		this.file = file;
	}

	/**
	 * Constructs a generic Bolota document exception.
	 *
	 * @param file File that is responsible for the exception.
	 */
	public BolotaFileException(File file) {
		this(file, "An unknown exception occurred");
	}

	/**
	 * Builds up a string in the format of "reason + ' while reading the Bolota document' +
	 * filename".
	 *
	 * @param reason Reason for the exception.
	 *
	 * @return Human-readable message with extra context around the file associated with the
	 *         exception.
	 */
	protected String buildReason(String reason) {
		return buildReason(reason, file);
	}


	/**
	 * Builds up a string in the format of "reason + ' while reading the Bolota document' +
	 * filename".
	 *
	 * @param reason Reason for the exception.
	 * @param file   File that is responsible for the exception.
	 *
	 * @return Human-readable message with extra context around the file associated with the
	 *         exception.
	 */
	protected static String buildReason(String reason, File file) {
		return reason + " while reading the Bolota document " + file.getName();
	}

	/**
	 * Gets the file that is associated with the exception.
	 *
	 * @return File that is associated with the exception.
	 */
	public File getFile() {
		return this.file;
	}
}
