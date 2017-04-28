package com.chrisali.controllers;

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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

import com.chrisali.model.*;

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
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "kttn";
		mockMvc.perform(get("/airports/charts/icao/{icaoCode}", icaoCode))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.info.icao", is("KTTN")));
				.andExpect(jsonPath("$.charts", isNotEmpty()));
	}
	
	@Test
	public void getChartsTestNotFound() throws Exception {
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "ktttttn";
		mockMvc.perform(get("/airports/charts/icao/{icaoCode}", icaoCode))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.statusCode", is("400")));
	}
}