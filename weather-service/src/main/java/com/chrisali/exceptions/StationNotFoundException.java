package com.chrisali.controllers;

import com.chrisali.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Weather Reporting Station Not Found") 
public class StationNotFoundException extends Exception {
	
	public StationNotFoundException(WeatherReport report) {
		super(report.getError());
	}
}