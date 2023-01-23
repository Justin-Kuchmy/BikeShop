package com.portfolioprojects.BikeShop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public interface EntityConfig {
    @Bean
    public RestTemplate RestTemplate();
}
