package com.chrisali.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chrisali.repositories.user.UserRepository;

@Configuration
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public UserDetailsService userDetailsService() {
		return (username) -> userRepository
				.findByUsername(username)
				.map(u -> new User(u.getUsername(), 
								u.getPassword(), 
								AuthorityUtils.createAuthorityList(u.getRolesAsString())
					))
				.orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
	}
}
