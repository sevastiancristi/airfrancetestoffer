package com.airfrance.testoffer.exceptions;

public class UserNotFoundControllerException extends RuntimeException {

	private static final long serialVersionUID = -1458068015632048582L;

	public UserNotFoundControllerException(Long id) {
		super("User with id " + id + " not found!");
	}

}
