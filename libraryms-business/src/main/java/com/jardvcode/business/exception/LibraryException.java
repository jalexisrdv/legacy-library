package com.jardvcode.business.exception;

public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LibraryException() {
		super();
	}

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

}
