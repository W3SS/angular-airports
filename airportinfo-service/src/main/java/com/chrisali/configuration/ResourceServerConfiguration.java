package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	private static String APPLICATION_NAME = "airportinfo";
	
	private static final Logger log = LogManager.getLogger(ResourceServerConfiguration.class);
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		log.info("==================================");
		log.info("Setting up Resource Server Security");
		log.info("==================================");
		
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/users/**").permitAll()
				.antMatchers(HttpMethod.GET, "/weather/**", "/airports/**").permitAll()
				.antMatchers(HttpMethod.DELETE, "/users/**").authenticated()
				.antMatchers(HttpMethod.DELETE, "/airports/**").hasAnyAuthority("ROLE_PREMIUM", "ROLE_ADMIN")
				.antMatchers(HttpMethod.POST, "/airports/**").hasAnyAuthority("ROLE_PREMIUM", "ROLE_ADMIN")
			.anyRequest().authenticated();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources
			.resourceId(APPLICATION_NAME);
	}
}