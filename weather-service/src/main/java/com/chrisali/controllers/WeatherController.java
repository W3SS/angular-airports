package com.chrisali.controllers;

import com.chrisali.model.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/weather/report")
public class WeatherController {
	
	private static final String URL_PREFIX = "https://avwx.rest/api/metar/";
	private static final String URL_SUFFIX = "?options=translate,info";
	
	@RequestMapping(value = "/icao/{icaoCode}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public WeatherReport getReport(@PathVariable String icaoCode) throws StationNotFoundException {
		String uriToHit = URL_PREFIX + icaoCode + URL_SUFFIX;
		
		RestTemplate restTemplate = new RestTemplate();
		WeatherReport report = restTemplate.getForObject(uriToHit, WeatherReport.class);
		
		if (report.getStation() == null) throw new StationNotFoundException(report);
		
		return report;
	}
}