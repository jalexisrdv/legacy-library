package com.jardvcode.web.shared.domain;

public class ParameterError {
	
	private String value;
	private String message;

	public ParameterError(String value, String message) {
		this.value = value;
		this.message = message;
	}
	
	public String getErrorMessage() {
		return value + " " + message;
	}

}
