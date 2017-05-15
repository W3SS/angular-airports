package com.chrisali.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AirportControllerTests {
	
	MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private AirportController airportController;
 
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void airportSearchTest() throws Exception {
		assertThat(airportController).isNotNull();
		
		String query = "paris";
		mockMvc.perform(get("/airports/search/{query}", query))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(6)));
				
		query = "egll";
		mockMvc.perform(get("/airports/search/{query}", query))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
				
		query = "eglirehgshl";
		mockMvc.perform(get("/airports/search/{query}", query))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
				
		query = "e";
		mockMvc.perform(get("/airports/search/{query}", query))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void getAirportTest() throws Exception {
		assertThat(airportController).isNotNull();

		Long id = 1L;
		mockMvc.perform(get("/airports/get/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.airportName", is("Goroka Airport")));

		id = 999999L;
		mockMvc.perform(get("/airports/get/{id}", id))
				.andExpect(status().isNotFound());
	}
}