package com.chrisali.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.chrisali.model.airportinfo", "com.chrisali.model.user"})
@EnableJpaRepositories(basePackages = {"com.chrisali.repositories.airportinfo", "com.chrisali.repositories.user"})
@EnableTransactionManagement
public class RepositoryConfiguration {}