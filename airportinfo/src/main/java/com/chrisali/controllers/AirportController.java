package com.chrisali.controllers;

import com.chrisali.repositories.AirportRepository;
import com.chrisali.domain.Airport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/airports/")
public class AirportController {
	
	@Autowired
	private AirportRepository airportRepository;
	
	@RequestMapping("search/{searchQuery}")
	public List<Airport> searchAirportsByQuery(@RequestParam(value = "searchQuery") String searchQuery) {
		return airportRepository.find(searchQuery);
	}
}