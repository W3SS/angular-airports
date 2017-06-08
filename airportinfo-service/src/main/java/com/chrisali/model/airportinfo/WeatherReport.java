package com.chrisali.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class WeatherReport {
	
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
	
	@JsonProperty("Temperature")	
	private String temperature;
	
	@JsonProperty("Dewpoint")
	private String dewpoint;
	
	@JsonProperty("Wind-Direction")
	private String windDir;
	
	@JsonProperty("Wind-Variable-Dir")
	private List<String> windVariableDir;
	
	@JsonProperty("Wind-Speed")
	private String windSpeed;
	
	@JsonProperty("Wind-Gust")
	private String windGust;
	
	@JsonProperty("Visibility")
	private String visibility;
	
	@JsonProperty("Altimeter")
	private String altimeter;
	
	@JsonProperty("Remarks-Info")
	private Map<String, String> remarksInfo;
	
	@JsonProperty("Other-List")
	private List<String> otherList;
	
	@JsonProperty("Cloud-List")
	private List<List<String>> cloudList;
	
	@JsonProperty("Runway-Visibility")
	private String runwayVisibility;
	
	@JsonProperty("Runway-Vis-List")
	private List<List<String>> runwayVisList;
	
	@JsonProperty("Flight-Rules")
	private String flightRules;
	
	@JsonProperty("Translations")
	private Map<String, String> translations;
}