package com.jardvcode.business.exception;

public class LoanException extends LibraryException {
	
	private static final long serialVersionUID = 1L;

	public LoanException() {
		super();
	}

	public LoanException(String message) {
		super(message);
	}

	public LoanException(String message, Throwable cause) {
		super(message, cause);
	}

}
