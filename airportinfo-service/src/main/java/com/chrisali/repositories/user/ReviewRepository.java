package com.chrisali.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chrisali.model.user.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	@Query("SELECT r FROM Review r WHERE r.airport.id = :airportId")
	public List<Review> findByAirportId(@Param(value = "airportId") Long airportId);
	
	@Query("SELECT r FROM Review r WHERE r.user.id = :userId")
	public List<Review> findByUserId(@Param(value = "userId") Long userId);
}
