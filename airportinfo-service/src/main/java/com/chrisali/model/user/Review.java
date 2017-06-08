package com.chrisali.model.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.chrisali.model.airportinfo.Airport;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="reviews")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Version
	@JsonIgnore
	private Long version;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "airports_id")
	private Airport airport;

	@NotNull
	private Date datePosted;
	
	@NotNull
	@Length(min = 5, max = 1000)
	private String text;
	
	@Range(min = 1, max = 5)
	private int rating;
	
	/*
	@OneToMany
	@JoinColumn(mappedBy = "comment", cascade = CascadeType.REMOVE)
	private Set<Comment> comments;
	
	private AtomicInteger helpful;
	
	private AtomicInteger voted;
	*/
	
	public Review() {}
}
