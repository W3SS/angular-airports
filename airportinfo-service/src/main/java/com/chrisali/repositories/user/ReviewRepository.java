package com.chrisali.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chrisali.model.user.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

	public void delete(Long id);
	
	@SuppressWarnings("unchecked")
	public Review save(Review Review);
	
	public Review findOne(Long id);
	
	@Query("SELECT FROM Review r where Airport.id = :airportId")
	public List<Review> findByAirportId(@Param(value = "airporId") Long airportId);
	
	@Query("SELECT FROM Review r where User.id = :userId")
	public List<Review> findByUserId(@Param(value = "userId") Long userId);
}
