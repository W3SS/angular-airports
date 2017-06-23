package com.chrisali.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chrisali.model.user.User;
import com.chrisali.services.UserServiceImpl;

@Configuration
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private UserServiceImpl userService;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService())
			.passwordEncoder(bcryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		return (username) -> { 
			User user = userService.findByUsername(username);
		
			Set<GrantedAuthority> grantedAuthorites = new HashSet<>();
			
			user.getRoles().forEach(role -> {
				grantedAuthorites.add(new SimpleGrantedAuthority(role.getName()));
			});
			
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorites);
		};
	}
}
