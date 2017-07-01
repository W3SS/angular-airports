package com.chrisali.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.RoleType;
import com.chrisali.model.user.User;

@SpringBootTest//(classes = {SecurityConfiguration.class})
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserServiceTests {

	@Autowired
	private UserServiceImpl userService;
	
	@Test
	public void registerUserTest() {
		User user = new User();
		user.setUsername("newUser@test.com");
		user.setRawPassword("test123!@#");
		user.setPasswordConfirm("test123!@#");
		
		assertTrue("User should be registered successfully", userService.registerUser(user));
		
		assertTrue("User should exist in database", userService.usernameExists(user.getUsername()));
		
		user.setUsername("badUser@test.com");
		user.setPassword("test123!@#");
		user.setPasswordConfirm("test123!@#!!");
		
		assertFalse("User with non-matching passwords should not be registered", userService.registerUser(user));
		
		assertFalse("User should not exist in database", userService.usernameExists(user.getUsername()));
	}
	
	@Test
	public void setUserRolesTest() {
		Set<Role> roles = userService.setRolesSet(RoleType.ADMIN);
				
		assertEquals("Set should contain all 3 roles", 3, roles.size());
		
		roles = userService.setRolesSet(RoleType.PREMIUM);
		
		assertEquals("Set should contain two roles", 2, roles.size());
		
		roles = userService.setRolesSet(RoleType.FREE);
		
		assertEquals("Set should contain only the free role", 1, roles.size());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	@WithMockUser("ADMIN")
	public void enableUserTest() {
		assertTrue("User should be enabled and saved", userService.enableDisableUser("free@test.com", true));
		
		User user = userService.findByUsername("free@test.com");
		
		assertTrue("User should be enabled in database", user.isEnabled());
		
		assertTrue("User should be disabled and saved", userService.enableDisableUser("free@test.com", false));
		
		user = userService.findByUsername("free@test.com");
		
		assertFalse("User should be disabled in database", user.isEnabled());
		
		// Should throw an exception
		userService.enableDisableUser("does not exist", true);			
	}
	
	@Test(expected = AuthenticationCredentialsNotFoundException.class)
	@WithMockUser("FREE")
	public void enableUserNoAuthTest() {
		enableUserTest();
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void deleteUserTest() {		
		assertTrue("User should have been successfully deleted from database", userService.deleteUser("free@test.com"));
		
		assertTrue("Nonexistant user should not have been deleted", userService.deleteUser("noexist@test.com"));
	}
}
