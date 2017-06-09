package com.chrisali.repositories.airportinfo;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisali.model.user.User;
import com.chrisali.repositories.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void getUsersTest() {
		List<User> users = userRepository.findAll();
		
		assertEquals("3 users should have been created", 3, users.size());
	}
	
	@Test
	public void deleteUserTest() {
		List<User> users = userRepository.findAll();
		
		userRepository.delete(users.get(0).getId());
		
		users = userRepository.findAll();
		
		assertEquals("2 users should remain", 2, users.size());
	}
}
