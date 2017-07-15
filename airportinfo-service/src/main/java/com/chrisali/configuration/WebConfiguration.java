package com.chrisali.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class WebConfiguration {
	
	private static final Logger log = LogManager.getLogger(WebConfiguration.class);
	private static final String MESSAGE_SOURCE = "com.chrisali.configuration.messages";
	private static final String MESSAGE_ENCODING = "UTF-8";
	
	@Bean(name = "messageSource")
	public MessageSource messageSource() {
		log.info("==================================");
		log.info("Setting up Message Source");
		log.info("==================================");
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE);
		messageSource.setCacheSeconds(5);
		messageSource.setDefaultEncoding(MESSAGE_ENCODING);
		
		return messageSource;
	}
}