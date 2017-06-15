package com.chrisali.repositories.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisali.model.user.Comment;
import com.chrisali.model.user.Review;
import com.chrisali.model.user.User;
import com.chrisali.repositories.user.CommentRepository;
import com.chrisali.repositories.user.ReviewRepository;
import com.chrisali.repositories.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTests {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Test
	public void getCommentsForUserTest() {
		User user = userRepository.findByUsername("free@test.com");
		
		assertNotNull("User should be in database", user);
		
		List<Comment> comments = commentRepository.findByUserId(user.getId());
		
		assertEquals("15 reviews should be in database for this user", 15, comments.size());
	}
	
	@Test
	public void getCommentsForReviewTest() {
		Review review = reviewRepository.findOne(3L);
				
		assertNotNull("Review should be in database", review);
		
		List<Comment> comments = commentRepository.findByUserId(review.getId());
		
		assertEquals("15 reviews should be in database for this airport", 15, comments.size());
	}
	
	@Test
	public void deleteReviewTest() {
		User user = userRepository.findByUsername("free@test.com");
		
		assertNotNull("User should be in database", user);
		
		List<Comment> comments = commentRepository.findByUserId(user.getId());
		
		int totalComments = comments.size();
		
		commentRepository.delete(comments.get(0).getId());
		
		comments = commentRepository.findByUserId(user.getId());
		
		assertEquals((totalComments - 1) + " reviews should remain", totalComments - 1, comments.size());
	}
}
