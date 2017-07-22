package com.chrisali.configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.chrisali.model.user.Role;
import com.chrisali.model.user.User;
import com.chrisali.repositories.user.UserRepository;

/**
 * Implementation of {@link UserDetailsService} used by {@link OAuth2Configuration} to
 * get a user from the database, authenticate it and then grant a token  
 * 
 * @author Christopher Ali
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {		
		User user = userRepository
			.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

		//TODO maybe try to get this into a functional interface
		return new CustomUserDetails(user);
	}

	/**
	 * Custom implementation of {@link UserDetails} used by {@link UserDetailsService} to
	 * authenticate a user 
	 * 
	 * @author christopher
	 *
	 */
	private static final class CustomUserDetails implements UserDetails {

		private static final long serialVersionUID = 1422110058915918839L;
		
		private Collection<? extends GrantedAuthority> authorities;
		private String username;
		private String password;
		private boolean enabled;
		
		public CustomUserDetails(User user) {
			this.username = user.getUsername();
			this.password = user.getPassword();
			this.authorities = translateRolesToAuthorites(user.getRoles());
			this.enabled = user.isEnabled();
		}
		
		public String getUsername() { return username; }

		public String getPassword() { return password; }

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() { return authorities;	}
		
		public Collection<GrantedAuthority> translateRolesToAuthorites(Set<Role> roles) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			
			for (Role role : roles) {
				String name = role.getName().toUpperCase();
				
				if (!name.startsWith("ROLE_")) name = "ROLE_" + name;
				
				authorities.add(new SimpleGrantedAuthority(name));
			}
			
			return authorities;
		}
		
		@Override
		public boolean isAccountNonExpired() { return enabled; }
		
		@Override
		public boolean isAccountNonLocked() { return enabled;	}
		
		@Override
		public boolean isCredentialsNonExpired() { return enabled; }

		@Override
		public boolean isEnabled() { return enabled; }
	}
}
