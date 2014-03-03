package com.muhammadHijazi.project1.mailHandler;

public class ErrorCodeException extends Exception {
	String eCode;

	public ErrorCodeException(String code) {
		eCode = code;
	}

	public String getECode() {
		return eCode;
	}
}
