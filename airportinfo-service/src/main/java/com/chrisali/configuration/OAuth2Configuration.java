package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class OAuth2Configuration {
	
	private static final Logger log = LogManager.getLogger(OAuth2Configuration.class);
	private static final String APPLICATION_NAME = "airportinfo";
	private static final int TOKEN_VALIDITY = 120 * 60; // 2 Hours
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
				
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		private AuthenticationManager authenticationManager;
				
		@Autowired
		private UserDetailsService userDetailsService;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			log.info("==================================");
			log.info("Setting up Authentication Server");
			log.info("==================================");
			
			endpoints
				.tokenStore(tokenStore)
				.userDetailsService(userDetailsService)
				.authenticationManager(authenticationManager);
		}
						
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security
				.checkTokenAccess("isAuthenticated()");
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients
				.inMemory()
					.withClient(APPLICATION_NAME)
					.authorizedGrantTypes("password", "authorization_code", "refresh_token")
					.authorities("ROLE_FREE", "ROLE_PREMIUM", "ROLE_ADMIN")
					.scopes("read", "write")
					.resourceIds(APPLICATION_NAME)
					.secret("test123");
		}
		
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(tokenStore);
			tokenServices.setAccessTokenValiditySeconds(TOKEN_VALIDITY);
			
			return tokenServices;
		}
	}
	
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		
		@Autowired
		private AuthenticationManager authenticationManager;

		@Override
		public void configure(HttpSecurity http) throws Exception {
			log.info("==================================");
			log.info("Setting up OAuth2 Security");
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
				.resourceId(APPLICATION_NAME)
				.authenticationManager(authenticationManager);
		}
	}
	
	@Configuration
	protected static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

		@Autowired
		private UserDetailsService userDetailsService;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
				
		@Override
		public void init(AuthenticationManagerBuilder auth) throws Exception {
			log.info("==================================");
			log.info("Setting up Authentication Manager");
			log.info("==================================");
			
			auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder);
		}
	}
}
