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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class WeatherControllerTests {
	
	MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Autowired
	private WeatherController weatherController;
 
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void getWeatherReportTest() throws Exception {
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "kttn";
		mockMvc.perform(get("/weather/report/icao/{icaoCode}", icaoCode))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.Station", is("KTTN")));
	}
	
	@Test
	public void getWeatherReportTestNotFound() throws Exception {
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "ktttttn";
		mockMvc.perform(get("/weather/report/icao/{icaoCode}", icaoCode))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.statusCode", is("400")));
	}
	
	@Test
	public void getWeatherForecastTest() throws Exception {
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "kttn";
		mockMvc.perform(get("/weather/forecast/icao/{icaoCode}", icaoCode))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.Station", is("KTTN")));
	}
	
	@Test
	public void getWeatherForecastTestNotFound() throws Exception {
		assertThat(weatherController).isNotNull();
		
		String icaoCode = "ktttttn";
		mockMvc.perform(get("/weather/forecast/icao/{icaoCode}", icaoCode))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.message", notNullValue()))
				.andExpect(jsonPath("$.statusCode", is("400")));
	}
}