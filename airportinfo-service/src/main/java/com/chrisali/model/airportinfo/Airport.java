package com.chrisali.model.airportinfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.chrisali.model.user.BaseUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * Contains various information about an airport; can be created by a premium user, and also has a list reviews created by free or premium users   
 * 
 * @author Christopher Ali
 *
 */
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
	private BaseUser user;
		
	public Airport() {}
}