package com.chrisali.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.chrisali.model.user.History;
import com.chrisali.model.user.Review;

public interface HistoryRepository extends JpaRepository<History, Long> {

	@Query("SELECT h FROM History h WHERE h.user.id = :userId ORDER BY dateVisited DESC")
	public List<Review> findByUserId(@Param(value = "userId") Long userId);
}
