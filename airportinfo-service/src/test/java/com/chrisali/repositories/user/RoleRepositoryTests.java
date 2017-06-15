package com.chrisali.repositories.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.RoleType;
import com.chrisali.model.user.User;
import com.chrisali.services.UserServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleRepositoryTests {

	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void findByUsersIdTest() {
		User user = userService.findByUsername("admin@test.com");
		
		Set<Role> roles = roleRepository.findByUsers_Id(user.getId());
		
		assertEquals("3 roles should be retrieved", 3, roles.size());
		
		user = userService.findByUsername("premium@test.com");
		
		roles = roleRepository.findByUsers_Id(user.getId());
		
		assertEquals("2 roles should be retrieved", 2, roles.size());
		
		user = userService.findByUsername("free@test.com");
		
		roles = roleRepository.findByUsers_Id(user.getId());
		
		assertEquals("1 roles should be retrieved", 1, roles.size());
	}
	
	@Test
	public void findByRoleNameTest() {
		Role free = roleRepository.findByName(RoleType.FREE.toString().toLowerCase());
		Role premium = roleRepository.findByName(RoleType.PREMIUM.toString().toLowerCase());
		Role admin = roleRepository.findByName(RoleType.ADMIN.toString().toLowerCase());
		
		assertNotNull("Role should not be null", free);
		assertNotNull("Role should not be null", premium);
		assertNotNull("Role should not be null", admin);
	}
}
