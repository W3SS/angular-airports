package com.chrisali.repositories.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisali.model.airportinfo.Airport;
import com.chrisali.model.user.Review;
import com.chrisali.model.user.User;
import com.chrisali.repositories.airportinfo.AirportRepository;
import com.chrisali.repositories.user.ReviewRepository;
import com.chrisali.repositories.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewRepositoryTests {

	@Autowired
	private AirportRepository airportRepository; 
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void getReviewsForUserTest() {
		User user = userRepository.findByUsername("free@test.com");
		
		assertNotNull("User should be in database", user);
		
		List<Review> reviews = reviewRepository.findByUserId(user.getId());
		
		assertEquals("4 reviews should be in database for this user", 4, reviews.size());
	}
	
	@Test
	public void getReviewsForAirportTest() {
		Airport airport = airportRepository.findOne(3L);
				
		assertNotNull("Airport should be in database", airport);
		
		List<Review> reviews = reviewRepository.findByAirportId(airport.getId());
		
		assertEquals("3 reviews should be in database for this airport", 3, reviews.size());
	}
	
	@Test
	public void deleteReviewTest() {
		User user = userRepository.findByUsername("free@test.com");
		
		assertNotNull("User should be in database", user);
		
		List<Review> reviews = reviewRepository.findByUserId(user.getId());
		
		int totalReviews = reviews.size();
		
		reviewRepository.delete(reviews.get(0).getId());
		
		reviews = reviewRepository.findByUserId(user.getId());
		
		assertEquals((totalReviews - 1) + " reviews should remain", totalReviews - 1, reviews.size());
	}
}
