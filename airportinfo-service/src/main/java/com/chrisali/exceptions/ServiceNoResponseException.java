package com.chrisali.controllers;

import com.chrisali.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.REQUEST_TIMEOUT, reason="Weather Reporting Service Not Responding") 
public class ServiceNoResponseException extends Exception {
	
	public ServiceNoResponseException() {
		super("The weather reporting service is not responding and cannot be reached at this time. Please try again later.");
	}
}