package com.chrisali.model.airportinfo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class WeatherForecast {
	
	@JsonProperty("Station")
	protected String station;
	
	@JsonProperty("Time")
	protected String time;
	
	@JsonProperty("Info")
	protected Map<String, String> info;
	
	@JsonProperty("Raw-Report")
	protected String rawReport;
	
	@JsonProperty("Units")
	protected Map<String, String> units;
	
	@JsonProperty("Error")
	protected String error;
	
	@JsonProperty("Remarks")
	protected String remarks;	
	
	@JsonProperty("Forecast")
	private Forecast[] forecasts;
	
	@JsonProperty("Max-Temp")
	private String maxTemp;
	
	@JsonProperty("Min-Temp")
	private String minTemp;
	
	@JsonProperty("Translations")
	private Map<String, Object> translations;
	
	@Data
	static class Forecast {
	
		@JsonProperty("Start-Time")
		private String startTime;
		
		@JsonProperty("End-Time")
		private String endTime;
		
		@JsonProperty("Wind-Speed")
		private Integer windSpeed;
		
		@JsonProperty("Wind-Shear")
		private String windShear;
		
		@JsonProperty("Wind-Gust")
		private Integer windGust;
		
		@JsonProperty("Visibility")
		private String visibility;
		
		@JsonProperty("Flight-Rules")
		private String flightRules;
		
		@JsonProperty("Probability")
		private String probability;
		
		@JsonProperty("Type")
		private String type;
		
		@JsonProperty("Cloud-List")
		private List<List<String>> cloudList;
		
		@JsonProperty("Other-List")
		private List<String> otherList;
		
		@JsonProperty("Icing-List")
		private List<String> icingList;
		
		@JsonProperty("Turb-List")
		private List<String> turbList;
		
		@JsonProperty("Temp-List")
		private List<String> tempList;
	}
}