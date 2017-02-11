package com.chrisali.repositories;

import com.chrisali.configuration.RepositoryConfiguration;
import com.chrisali.domain.Airport;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class AirportRepositoryTests {
	
	@Autowired
	private AirportRepository airportRepository;
	
	@Before
	public void init() {
		
	}
	
	@Test
	public void searchAirportTests() {
		
	}
}