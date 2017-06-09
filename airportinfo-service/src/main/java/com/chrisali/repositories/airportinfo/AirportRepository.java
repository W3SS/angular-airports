package com.chrisali.repositories.airportinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chrisali.model.airportinfo.Airport;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
	
	/**
	*	Searches for an group of airports using a single search query string that searches by airport name, city, country and IATA/ICAO code
	*   @param searchQuery
	*/
	@Query("SELECT a from Airport a WHERE LOWER(a.airportName) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(a.city) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(a.country) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(a.iataCode) LIKE LOWER(CONCAT('%', :searchQuery, '%')) OR LOWER(a.icaoCode) LIKE LOWER(CONCAT('%', :searchQuery, '%'))")
	public List<Airport> find(@Param(value = "searchQuery") String searchQuery);
}