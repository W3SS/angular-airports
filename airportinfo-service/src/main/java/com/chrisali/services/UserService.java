package com.chrisali.services;

import java.util.Set;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.RoleType;
import com.chrisali.model.user.User;

public interface UserService {
	
	/**
	 * Registers a new user into the database. This method is not secured. 
	 * 
	 * @param user
	 * @return the result of the registration (pass [true] or fail [false])
	 */
	public boolean registerUser(User user);
	
	/**
	 * Deletes a user from the database. This method is restricted to authenticated users.
	 * 
	 * @param username
	 * @return the result of the registration (pass [true] or fail [false])
	 */
	public boolean deleteUser(String username);
	
	/**
	 * Enables or disables a user's account. This method is restricted to admin users.
	 * 
	 * @param username
	 * @param enabled
	 * @return
	 */
	public boolean enableDisableUser(String username, boolean enabled);
	
	/**
	 * Changes the role of a user to the specified role in roleType. This method is restricted to admin users. 
	 * 
	 * @param username
	 * @param roleType
	 * @return
	 */
	public boolean upgradeUser(String username, RoleType roleType);
	
	/**
	 * @param username
	 * @return if a user with the user name username exists in the database. This method is not secured.
	 */
	public boolean usernameExists(String username);

	/**
	 * Creates a set of roles based on the provided RoleType. Each role has a heiarchy of lower privileges it is granted. 
	 * For example, Admin also has premium and free privileges, and premium also has free privileges 
	 * 
	 * @param roleName
	 * @return set of roles
	 */
	public Set<Role> setRolesSet(RoleType roleType);
	
	/**
	 * Attempts to find a user by its username 
	 * 
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException if username is not found
	 */
	public User findByUsername(String username);
}
