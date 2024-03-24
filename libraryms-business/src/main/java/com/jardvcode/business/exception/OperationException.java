package com.jardvcode.business.exception;

public class OperationException extends LibraryException {
	
	private static final long serialVersionUID = 1L;

	public OperationException() {
		super();
	}

	public OperationException(String message) {
		super(message);
	}

	public OperationException(String message, Throwable cause) {
		super(message, cause);
	}

}
