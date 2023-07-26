package com.justinkuchmy.customer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CustomerConfig {
    
    @Bean
    public WebClient.Builder getWebClientbuilder()
    {
        return WebClient.builder();
    }
}
