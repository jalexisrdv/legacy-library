package com.jardvcode.business.exception;

public class PenaltyException extends LibraryException {
	
	private static final long serialVersionUID = 1L;

	public PenaltyException() {
		super();
	}

	public PenaltyException(String message) {
		super(message);
	}

	public PenaltyException(String message, Throwable cause) {
		super(message, cause);
	}

}
