package com.airfrance.testoffer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserControllerAdvice {
	@ResponseBody
	@ExceptionHandler(UserNotFoundControllerException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(UserNotFoundControllerException exception) {
		return exception.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler(BadSearchRequestControllerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String badSearchRequestHandler(BadSearchRequestControllerException exception) {
		return exception.getMessage();
	}
}
