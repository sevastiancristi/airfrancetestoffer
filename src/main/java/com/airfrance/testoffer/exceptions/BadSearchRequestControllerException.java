package com.airfrance.testoffer.exceptions;

public class BadSearchRequestControllerException extends RuntimeException {

	private static final long serialVersionUID = -8264897493807248688L;

	public BadSearchRequestControllerException() {
		super("All fields are null!");
	}
}
