package com.chrisali.exceptions;

import com.chrisali.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Weather Reporting Station Not Found") 
public class StationNotFoundException extends Exception {

	private static final long serialVersionUID = 1870698125486015938L;

	public StationNotFoundException(WeatherReport report) {
		super (report.getError());
	}
	
	public StationNotFoundException(WeatherForecast forecast) {
		super (forecast.getError());
	}
}