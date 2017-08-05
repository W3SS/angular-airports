package com.chrisali.model.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.chrisali.model.airportinfo.Airport;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "history")
public class History {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "users_id")
	private BaseUser user;
	
	private Date dateVisited;
	
	@JsonIgnore
	@OneToMany(mappedBy = "history", cascade = CascadeType.ALL)
	private Airport visitedAirport;
	
	public History() {}
}
