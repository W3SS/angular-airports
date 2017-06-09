package com.chrisali.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisali.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByUsername(String username);
}
