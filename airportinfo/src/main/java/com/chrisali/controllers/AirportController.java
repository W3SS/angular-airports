package com.chrisali.controllers;

import com.chrisali.repositories.AirportRepository;
import com.chrisali.model.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.List;

@RestController
@RequestMapping("/airports/")
public class AirportController {
	
	@Autowired
	private AirportRepository airportRepository;
	
	@RequestMapping(value = "search/{query}", method = RequestMethod.GET)
	public List<Airport> searchAirportsByQuery(@PathVariable(value = "query") String query) {
		return query.length() > 2 ? airportRepository.find(query) : null;
	}
}