package com.chrisali.services;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.RoleType;
import com.chrisali.model.user.User;
import com.chrisali.repositories.user.RoleRepository;
import com.chrisali.repositories.user.UserRepository;

/**
 * Contains an implementation of methods to register, en/disable, up/downgrade and delete Angular Airport users
 * 
 * @author Christopher Ali
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Logger logger = LogManager.getLogger(UserServiceImpl.class);
	
	@Override
	public boolean registerUser(User user) {
		if (user.getRawPassword() != user.getPasswordConfirm())
			return false;
		
		try {
			user.setPassword(passwordEncoder.encode(user.getRawPassword()));
			//user.setEnabled(false);
			
			Role role = roleRepository.findByName(RoleType.FREE.toString().toLowerCase());
			user.setRoles(new HashSet<Role>(Collections.unmodifiableList(Arrays.asList(role))));;
			
			userRepository.save(user);
			
			//emailService.sendEmail();
			
			logger.info("Successfully registered user with username: " + user.getUsername());
		} catch (Exception e) {
			logger.error("Unable to register user: " + user.getUsername() + "!", e);
			
			return false;
		}
		
		return true;
	}

	@Secured("ROLE_ADMIN")
	@Override
	public boolean upgradeUser(String username, RoleType roleName) {
		User user = findByUsername(username);
		
		user.setRoles(setRolesSet(roleName));
		
		userRepository.save(user);
		
		logger.info("Changed " + user.getUsername() + " to " + roleName.toString().toLowerCase());
		
		return true;
	}
	
	/**
	 * Creates a set of roles based on the provided RoleType. Each role has a heiarchy of lower privileges it is granted. 
	 * For example, Admin also has premium and free privileges, and premium also has free privileges 
	 * 
	 * @param roleName
	 * @return set of roles
	 */
	public Set<Role> setRolesSet(RoleType roleName) {
		Set<Role> roles = new HashSet<Role>();
		
		switch (roleName) {
		case ADMIN:
			roles.add(roleRepository.findByName(RoleType.ADMIN.toString().toLowerCase()));
		case PREMIUM:
			roles.add(roleRepository.findByName(RoleType.PREMIUM.toString().toLowerCase()));
		case FREE:
		default:
			roles.add(roleRepository.findByName(RoleType.FREE.toString().toLowerCase()));			
			break;
		}
		
		return roles;
	}

	@Secured({"ROLE_FREE", "ROLE_PREMIUM", "ROLE_ADMIN"})
	@Override
	public boolean deleteUser(String username) {
		User user = findByUsername(username);
		
		boolean success = false;
		try {			
			userRepository.delete(user.getId());
			success = !usernameExists(username);
			
			logger.info("Successfully deleted user: " + username);
		} catch (Exception e) {
			logger.error("Could not delete user: " + username, e);
			
			success = false;
		}
		
		return success;
	}

	@Secured("ROLE_ADMIN")
	@Override
	public boolean enableDisableUser(String username, boolean enabled) {
		User user = findByUsername(username);
		
		user.setEnabled(enabled);
		
		try {
			userRepository.save(user);
			
			logger.info("Changed status of user: " + username + " to " + (enabled ? "enabled" : "disabled"));
		} catch (Exception e) {
			logger.error("Could not change status for user: " + username, e);
			
			return false;
		}
		
		return true;
	}

	@Override
	public boolean usernameExists(String username) {
		return userRepository.findByUsername(username) != null;
	}
	
	/**
	 * Attempts to find a user by its username 
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException if username is not found
	 */
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		
		if (user == null)
			throw new UsernameNotFoundException("Could not find a user with the username: " + username);
		
		return user;
	}
}
