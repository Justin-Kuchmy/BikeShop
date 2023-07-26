package com.justinkuchmy.product;

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

@SpringBootApplication
@RestController
@RequestMapping(path = "/")
@EnableMongoRepositories(basePackages = "com.justinkuchmy.product")
public class ProductApplication extends AbstractMongoClientConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

    @GetMapping
    public String Home()
    {
        return "Products Started on port 8084";
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
