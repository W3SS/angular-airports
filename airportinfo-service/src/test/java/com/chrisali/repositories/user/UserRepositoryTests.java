package com.chrisali.repositories.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.RoleType;
import com.chrisali.model.user.User;
import com.chrisali.repositories.user.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void getUsersTest() {
		List<User> users = userRepository.findAll();
		
		assertEquals("3 users should be in database", 3, users.size());
	}
	
	@Test
	public void findSingleUserTest() {
		User user = userRepository.findByUsername("free@test.com");
		
		assertNotNull("User should exist in database", user);
		
		user = userRepository.findByUsername("doesnotexist@test.com");
		
		assertNull("User should not exist in database", user);
	}
	
	@Test
	public void createNewUserTest() {
		User user = new User();
		user.setUsername("newuser@test.com");
		user.setPassword("test123!@#");
		user.setEnabled(true);
		
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleRepository.findByName(RoleType.FREE.toString().toLowerCase()));
		user.setRoles(roles);
		
		userRepository.save(user);
		
		assertNotNull("Created user should exist in database", userRepository.findByUsername(user.getUsername()));
	}
	
	@Test
	public void editUserTest() {
		User user = userRepository.findByUsername("free@test.com");
		String newPass = "test123456!@#";
		Long oldVersion = user.getVersion();
		
		user.setPassword(newPass);
		
		userRepository.save(user);
		
		user = userRepository.findByUsername("free@test.com");
		
		assertEquals("Password should have been updated", newPass, user.getPassword());
		
		assertNotEquals("Version numbers should have been updated", oldVersion, user.getVersion());
	}
	
	@Test
	public void deleteUserTest() {
		List<User> users = userRepository.findAll();
		int totalUsers = users.size();
		
		userRepository.delete(users.get(0).getId());
		
		users = userRepository.findAll();
		
		assertEquals((totalUsers - 1) + " users should remain", totalUsers - 1, users.size());
	}
}
