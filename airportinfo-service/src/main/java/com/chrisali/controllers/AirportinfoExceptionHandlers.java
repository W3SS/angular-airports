package com.chrisali.controllers;

import com.chrisali.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotWritableException;

@ControllerAdvice
public class AirportinfoExceptionHandlers {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);
	
	@ExceptionHandler(HttpMessageNotWritableException.class)
	public ResponseEntity<ErrorInfo> handleException(HttpMessageNotWritableException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage("The service was unable to find the requested airport");
		info.setStatusCode(HttpStatus.NOT_FOUND.toString());
		info.setHtml("");
		
		logger.error("Unable to retrieve airport: " + ex.getMessage());
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}
}