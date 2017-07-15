package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger log = LogManager.getLogger(SecurityConfiguration.class);
	private static final String REMEMBER_ME_KEY = "remember-me-pls";
	private static final int TOKEN_VALIDITY = 3600 * 24 * 14; // Token is valid for two weeks
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info("==================================");
		log.info("Setting up Web Security");
		log.info("==================================");
		
		http.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users/**").permitAll()
				.antMatchers(HttpMethod.GET, "/weather/**", "/airports/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/users/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/airports/**").hasAnyAuthority("ROLE_PREMIUM", "ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/airports/**").hasAnyAuthority("ROLE_PREMIUM", "ROLE_ADMIN")
				.anyRequest().authenticated()
			.and()
				.formLogin()
					.loginPage("/login").permitAll()
					.loginProcessingUrl("/dologin")
			.and()
				.logout()
					.logoutUrl("/logout").permitAll()
			.and()
				.rememberMe()
				.rememberMeServices(rememberMeServices())
				.key(REMEMBER_ME_KEY)
				.tokenValiditySeconds(TOKEN_VALIDITY) 
			.and()
				.csrf();
	}

	@Bean
	public TokenBasedRememberMeServices rememberMeServices() {
		return new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
	}
}