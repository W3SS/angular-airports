package com.chrisali.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisali.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public void delete(Long id);
	
	@SuppressWarnings("unchecked")
	public User save(User user);
	
	public User findOne(Long id);
	
	public List<User> findAll();
}
