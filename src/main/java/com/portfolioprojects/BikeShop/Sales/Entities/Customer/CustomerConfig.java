package com.portfolioprojects.BikeShop.Sales.Entities.Customer;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Configuration;
import com.portfolioprojects.BikeShop.EntityConfig;

@Configuration
public class CustomerConfig implements EntityConfig  {

    @Bean
    @Override
    public RestTemplate RestTemplate()
    {
        return new RestTemplate();
    }
    
}
