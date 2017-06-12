package com.chrisali.model.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;

import com.chrisali.model.airportinfo.Airport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Subclass of BaseUser that adds relations for AngularAirports' reviews, favorites, comments, etc
 * 
 * @author Christopher Ali
 *
 */
@Data
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EqualsAndHashCode(exclude={"roles", "favorites", "reviews"}, callSuper=false)
public class User extends BaseUser {
	
	// TODO Encrypt and include special chars/numbers in bean validation
	@Length(min = 8, max = 16)
	@Transient
	private String rawPassword;
	
	@Length(min = 8, max = 16)
	@Transient
	private String passwordConfirm;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private Set<Role> roles;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Airport> favorites;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
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
