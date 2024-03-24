package com.jardvcode.business.exception;

public class NoDataFoundException extends LibraryException{
	
	private static final long serialVersionUID = 1L;

	public NoDataFoundException() {
		super();
	}

	public NoDataFoundException(String message) {
		super(message);
	}

	public NoDataFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
