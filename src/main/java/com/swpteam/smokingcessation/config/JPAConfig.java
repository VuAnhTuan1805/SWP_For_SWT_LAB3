package com.swpteam.smokingcessation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.swpteam.smokingcessation.repository")
public class JPAConfig {
}
