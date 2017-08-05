package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;

@Configuration
public class AuthorizationServerConfiguration {
	
	private static final Logger log = LogManager.getLogger(AuthorizationServerConfiguration.class);
	private static String APPLICATION_NAME = "airportinfo";
	private static String SECRET = "test123";
	
	@ActiveProfiles("test")
	@Configuration
	@EnableAuthorizationServer
	protected static class TestAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter { 
				
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			log.info("==================================");
			log.info("Setting up Test Authorization Server");
			log.info("==================================");
			
			endpoints
				.tokenStore(tokenStore)
				.authenticationManager(authenticationManager);
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
					.secret(SECRET);
		}
		
		@Bean
		@Primary
		public DefaultTokenServices tokenServices() {
			DefaultTokenServices tokenServices = new DefaultTokenServices();
			tokenServices.setSupportRefreshToken(true);
			tokenServices.setTokenStore(tokenStore);
			
			return tokenServices;
		}
	}
}
