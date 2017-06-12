package com.chrisali.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chrisali.model.user.Comment;
import com.chrisali.model.user.Review;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query("SELECT c FROM Comment c where c.review.id = :reviewId")
	public List<Review> findByReviewId(@Param(value = "reviewId") Long reviewId);
	
	@Query("SELECT c FROM Comment c where c.user.id = :userId")
	public List<Review> findByUserId(@Param(value = "userId") Long userId);
}
