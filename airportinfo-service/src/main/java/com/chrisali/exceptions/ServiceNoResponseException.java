package com.chrisali.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.REQUEST_TIMEOUT, reason="Weather Reporting Service Not Responding") 
public class ServiceNoResponseException extends Exception {
	
	private static final long serialVersionUID = -8491617506702823489L;

	public ServiceNoResponseException() {
		super("The weather reporting service is not responding and cannot be reached at this time. Please try again later.");
	}
}