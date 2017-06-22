package com.chrisali.services;

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
}
