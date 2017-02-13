package com.chrisali.model;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "airports")
public class Airport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	@JsonIgnore
	private Long version;
	
	private String airportName;
	
	private String city;
	
	private String country;
	
	private String iataCode;
	
	private String icaoCode;
	
	private Float latitude;
	
	private Float longitude;
	
	private Float altitude;
	
	private Float timeZone;
	
	private String tzTimeZone;
	
	public Airport() {}
}