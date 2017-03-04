package com.chrisali.repositories;

import com.chrisali.model.Airport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AirportRepositoryTests {
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Test
	public void searchAirportTests() {
	
		String query = "Paris";
		List<Airport> airportSearch = airportRepository.find(query);
		
		assertNotNull(airportSearch);
		assertEquals("6 results should be returned for query " + query, 6, airportSearch.size());
		
		query = "rJtT";
		airportSearch = airportRepository.find(query);
		
		assertNotNull(airportSearch);
		assertEquals("1 result should be returned for query " + query, 1, airportSearch.size());
		
		Airport result = airportSearch.get(0);
		assertNotNull(result);
		
		assertEquals("Search result should be Tokyo Haneda airport", "Tokyo Haneda International Airport", result.getAirportName());
		assertEquals("Search result's IATA code should be 'HND'", "HND", result.getIataCode());
		assertEquals("Search result should be in Japan", "Japan", result.getCountry());
		
		query = "bb950iwkeob";
		
		airportSearch = airportRepository.find(query);
		
		assertNotNull(airportSearch);
	}

	@Test
	public void getAirportByIdTests() {
		String query = "kttn";
		List<Airport> airportSearch = airportRepository.find(query);

		assertNotNull(airportSearch);
		assertEquals("1 Airport result should be returned for query " + query, 1, airportSearch.size());

		Airport airportFromList = airportSearch.get(0);
		assertNotNull(airportFromList);

		Airport retrievedAirport = airportRepository.getOne(airportFromList.getId());
		assertNotNull(retrievedAirport);

		assertEquals("Retrieved airport should equal airport from list", airportFromList.getAirportName(), retrievedAirport.getAirportName());
	}
}