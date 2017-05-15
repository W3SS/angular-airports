package com.chrisali.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
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
public class AirportChartsControllerTests {
	
	MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private AirportChartsController chartsController;
 
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void getChartsTest() throws Exception {
		assertThat(chartsController).isNotNull();
		
		String icaoCode = "kttn";
		mockMvc.perform(get("/airports/charts/icao/{icaoCode}", icaoCode))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.info.id", is("KTTN")))
				.andExpect(jsonPath("$.charts", Matchers.hasKey("General")))
				.andExpect(jsonPath("$.charts", Matchers.hasKey("Approach")));
	}
	
	@Test
	public void getChartsTestNotFound() throws Exception {
		assertThat(chartsController).isNotNull();
		
		String icaoCode = "ktttttn";
		mockMvc.perform(get("/airports/charts/icao/{icaoCode}", icaoCode))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.statusCode", is("404")));
	}
}