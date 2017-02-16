package com.chrisali.controllers;

import com.chrisali.model.*;
import com.chrisali.exceptions.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class WeatherServiceExceptionHandlers {
	
	private Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	@ExceptionHandler(StationNotFoundException.class)
	public ResponseEntity<ErrorInfo> handleException(StationNotFoundException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage(ex.getMessage());
		info.setStatusCode(HttpStatus.BAD_REQUEST.toString());
		info.setHtml("");
		
		logger.error(ex.getMessage());
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ServiceNoResponseException.class)
	public ResponseEntity<ErrorInfo> handleException(ServiceNoResponseException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage(ex.getMessage());
		info.setStatusCode(HttpStatus.REQUEST_TIMEOUT.toString());
		info.setHtml("");
		
		logger.error(ex.getMessage());
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(UnknownHostException.class)
	public ResponseEntity<ErrorInfo> handleException(UnknownHostException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage(ex.getMessage());
		info.setStatusCode(HttpStatus.NOT_FOUND.toString());
		info.setHtml("");
		
		logger.error("Unable to find the following host: " + ex.getMessage());
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConnectException.class)
	public ResponseEntity<ErrorInfo> handleException(ConnectException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage("Unable to connect to the web service; connection timed out");
		info.setStatusCode(HttpStatus.REQUEST_TIMEOUT.toString());
		info.setHtml("");
		
		logger.error("Unable to connect to the web service; connection timed out");
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.REQUEST_TIMEOUT);
	}
	
	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<ErrorInfo> handleException(HttpServerErrorException ex) {
		ErrorInfo info = new ErrorInfo();
		info.setMessage("Internal error processing request");
		info.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		info.setHtml("");
		
		logger.error("Internal error processing request");
		
		return new ResponseEntity<ErrorInfo>(info, HttpStatus.REQUEST_TIMEOUT);
	}
}