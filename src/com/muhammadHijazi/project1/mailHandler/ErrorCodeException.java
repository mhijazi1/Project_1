package com.muhammadHijazi.project1.mailHandler;

/*
 * Simple exception for dealing with error codes
 */
public class ErrorCodeException extends Exception {
	private String eCode;

	public ErrorCodeException(String code) {
		eCode = code;
	}

	public String getECode() {
		return eCode;
	}
}
