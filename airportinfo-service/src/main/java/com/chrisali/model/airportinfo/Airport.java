package com.chrisali.model.airportinfo;

import javax.persistence.*;

import com.chrisali.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@Entity
@Table(name = "airports")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;
	
	public Airport() {}
}