package com.chrisali.model;

import java.util.Map;
import java.util.List;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@JsonInclude(Include.NON_NULL)
public class WeatherReport {
	
	@JsonProperty("Station")
	private String station;
	
	@JsonProperty("Info")
	private Map<String, String> info;
	
	@JsonProperty("Raw-Report")
	private String rawReport;
	
	@JsonProperty("Temperature")	
	private String temperature;
	
	@JsonProperty("Dewpoint")
	private String dewpoint;
	
	@JsonProperty("Wind-Direction")
	private Integer windDir;
	
	@JsonProperty("Wind-Variable-Dir")
	private List<Integer> windVariableDir;
	
	@JsonProperty("Wind-Speed")
	private Integer windSpeed;
	
	@JsonProperty("Wind-Gust")
	private Integer windGust;
	
	@JsonProperty("Visibility")
	private String visibility;
	
	@JsonProperty("Altimeter")
	private String altimeter;
	
	@JsonProperty("Remarks")
	private String remarks;
	
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
	
	@JsonProperty("Units")
	private Map<String, String> units;
	
	@JsonProperty("Error")
	private String error;
}