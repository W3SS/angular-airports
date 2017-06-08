package com.chrisali.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.chrisali.model.airportinfo.Airport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	@JsonIgnore
	private Long version;
	
	@NotNull
	@Length(min = 5, max = 30)
	private String username;
	
	// TODO Encrypt and include special chars/numbers
	@NotNull
	@Length(min = 8, max = 16)
	@Transient
	private String rawPassword;
	
	@NotNull
	private String password;
	
	@NotNull
	private String role;
	
	private boolean isEnabled;
	
	@JsonIgnore
	@OneToMany(mappedBy = "airport", cascade = CascadeType.REMOVE)
	private Set<Airport> favorites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE)
	private Set<Review> reviews;
	
	/*
	@JsonIgnore
	@OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
	private Set<Comment> comments;
		
	@JsonIgnore
	@Size(max = 10)
	//@OneToMany(mappedby = "airport", cascade = CascadeType.REMOVE)
	private Set<Airport> history;   
	*/
	
	public User() {}
}
