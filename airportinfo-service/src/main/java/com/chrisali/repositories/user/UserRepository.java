package com.chrisali.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisali.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/**
	 * @param username
	 * @return User object if it exists in the database, otherwise null
	 */
	public User findByUsername(String username);
}
