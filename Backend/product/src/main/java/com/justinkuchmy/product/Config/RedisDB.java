package com.justinkuchmy.product.Config;

import java.util.ArrayList;

import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.justinkuchmy.product.Entities.Product;
import com.justinkuchmy.product.FieldStrategy.IProductFieldStrategy;
import com.justinkuchmy.product.Entities.CustomObjectIdSerializer;
import com.justinkuchmy.product.ProductRepository;

import redis.clients.jedis.JedisPool;

@Component
public class RedisDB {
    JedisPool jedisPool = new JedisPool("localhost", 6379);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private List<Product> redisDataFromfield(String field)
    {
        try
        {
            var cachedValue = redisTemplate.opsForHash().get("product",field);
            if (cachedValue != null) {
                // Value is found in cache, return it
                return parseCachedValue(cachedValue.toString());
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    private List<Product> parseCachedValue(String cachedValue)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var ProductArray = objectMapper.readValue(cachedValue, Product[].class);
            List<Product> ProductList = new ArrayList<>();
            for (Product Product : ProductArray) {
                ProductList.add(Product);
            }
            return ProductList;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
        private String serializeProduct(List<Product> ProductData, String field)
    {
         ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(ObjectId.class, new CustomObjectIdSerializer()));
        try {
            String json = mapper.writeValueAsString(ProductData);

            // Store the JSON string in Redis
            redisTemplate.opsForHash().put("product",field, json);
            return json.toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    public List<Product> GetData(IProductFieldStrategy field, Product... Product)
    {
        try {
            var redisData = redisDataFromfield(field.getField());
            if(redisData != null)
            {
                return redisData;
            }
            List<Product> EntityList = new ArrayList<Product>();
            EntityList = field.getData(productRepository);
   
            // Cache the value
            serializeProduct(EntityList, field.getField()); 

            return EntityList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
             return null;
        }
    }

}
