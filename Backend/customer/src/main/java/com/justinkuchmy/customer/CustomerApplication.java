package com.justinkuchmy.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController 
@RequestMapping("/")
@EnableMongoRepositories(basePackages = "com.justinkuchmy.customer")
public class CustomerApplication extends AbstractMongoClientConfiguration{

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

    @GetMapping("")
    public String home(HttpServletRequest request) {
        return "Running on port 8081";
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
