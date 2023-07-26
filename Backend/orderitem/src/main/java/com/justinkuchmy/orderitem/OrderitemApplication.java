package com.justinkuchmy.orderitem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController 
@RequestMapping("/")
@EnableMongoRepositories(basePackages = "com.justinkuchmy.orderitem")
public class OrderitemApplication extends AbstractMongoClientConfiguration{

	public static void main(String[] args) {
		SpringApplication.run(OrderitemApplication.class, args);
	}


    @Bean
    public WebClient.Builder getWebClientBuilder() 
    {
        return WebClient.builder();
    }


    @GetMapping("")
    public String home(HttpServletRequest request) {
        return "Running on port 8083";
    }
    
    @Override
    protected String getDatabaseName() {
        return "BikeShopDB"; 
    }

    @Value("${spring.data.mongodb.uri}")
    private String ConnectionString;

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(ConnectionString);
    }

}
