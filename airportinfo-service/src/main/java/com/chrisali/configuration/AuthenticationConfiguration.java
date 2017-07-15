package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chrisali.repositories.user.UserRepository;

@Configuration
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {
	
	private static final Logger logger = LogManager.getLogger(AuthenticationConfiguration.class);

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		logger.debug("==================================");
		logger.debug("Setting up Authentication Manager");
		logger.debug("==================================");
		
		auth.userDetailsService(userDetailsService())
			.passwordEncoder(bcryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return (username) -> userRepository
				.findByUsername(username)
				.map(u -> new User(u.getUsername(), 
								u.getPassword(), 
								AuthorityUtils.createAuthorityList(u.getRolesAsString())
					))
				.orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
	}
}
