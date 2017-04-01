package com.chrisali.controllers;

import com.chrisali.model.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/weather/")
@CrossOrigin(origins = "http://localhost:8080")
public class WeatherController {
	
	private static final String URL_PREFIX = "https://avwx.rest/api/";
	private static final String[] URL_TYPES = new String[] { "metar/", "taf/" };
	private static final String URL_SUFFIX = "?options=translate,info";
	
	@RequestMapping(value = "report/icao/{icaoCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public WeatherReport getReport(@PathVariable String icaoCode) throws StationNotFoundException {
		String urlToHit = URL_PREFIX + URL_TYPES[0] + icaoCode + URL_SUFFIX;
		
		RestTemplate restTemplate = new RestTemplate();
		WeatherReport report = restTemplate.getForObject(urlToHit, WeatherReport.class);
		
		if (report.getStation() == null) throw new StationNotFoundException(report);
		
		return report;
	}
	
	@RequestMapping(value = "forecast/icao/{icaoCode}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public WeatherForecast getForecast(@PathVariable String icaoCode) throws StationNotFoundException {
		String urlToHit = URL_PREFIX + URL_TYPES[1] + icaoCode + URL_SUFFIX;
		
		RestTemplate restTemplate = new RestTemplate();
		WeatherForecast forecast = restTemplate.getForObject(urlToHit, WeatherForecast.class);
		
		if (forecast.getStation() == null) throw new StationNotFoundException(forecast);
		
		return forecast;
	}
}