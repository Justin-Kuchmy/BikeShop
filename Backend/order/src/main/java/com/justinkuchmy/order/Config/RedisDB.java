package com.justinkuchmy.order.Config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.justinkuchmy.order.Entities.Order;
import com.justinkuchmy.order.FieldStrategy.IOrderFieldStrategy;
import com.justinkuchmy.order.Entities.CustomObjectIdSerializer;

import com.justinkuchmy.order.OrderRepository;

@Component
public class RedisDB {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public RedisDB(OrderRepository orderRepository, RedisTemplate<String, String> redisTemplate) {
        this.orderRepository = orderRepository;
        this.redisTemplate = redisTemplate;
    }
    public Long getNextOrderItemIdFromRepo()
    {
        return this.orderRepository.count()+1;
    }
    private List<Order> redisDataFromField(String Field)
    {
        var cachedValue = redisTemplate.opsForHash().get("order",Field);
        if (cachedValue != null) {
            // Value is found in cache, return it
            if(!cachedValue.equals("null"))
            {
                return parseCachedValue(cachedValue.toString());
            }
        }
        return null;
    }
    private List<Order> parseCachedValue(String cachedValue)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var OrderArray = objectMapper.readValue(cachedValue, Order[].class);
            List<Order> OrderList = new ArrayList<>();
            for (Order Order : OrderArray) {
                OrderList.add(Order);
            }
            return OrderList;
        } catch (Exception e)
        {
            throw new RedisOperationException("",e);
        }
    }
    private String serializeOrder(List<Order> OrderData, String Field)
    {
         ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(ObjectId.class, new CustomObjectIdSerializer()));
        try {
            String json = mapper.writeValueAsString(OrderData);

            // Store the JSON string in Redis
            redisTemplate.opsForHash().put("order", Field, json);
            return json.toString();
        } catch (JsonProcessingException e) {
            throw new RedisOperationException("",e);
            
        }
    }
    private List<Order> getOrCreateOriginalList() {
        var existingCache = redisTemplate.opsForHash().get("order", "all");
        if (existingCache == null) {
            return orderRepository.findAll();
        } else {
            return parseCachedValue(existingCache.toString());
        }
    }
    
    private void updateExistingRedisEntry(List<Order> NewData)
    {
        List<Order> originalList =  getOrCreateOriginalList();
        originalList.addAll(NewData);
        putToHash(originalList);

    }
    private void deleteFromExistingRedisEntry(Long OrderIdToDelete)
    {
        //get all Order Data
        List<Order> originalList = getOrCreateOriginalList();
        originalList.removeIf(Order -> Order.getOrderId().equals(OrderIdToDelete));
        putToHash(originalList);
       
    }

    private void putToHash(List<Order> originalList)
    {
        redisTemplate.opsForHash().put("order","all", serializeOrder(originalList, "all"));
        redisTemplate.expire("order", Duration.ofSeconds(3600));
    }

    public List<Order> GetData(IOrderFieldStrategy field, Order... Order)
    {
        try {
            var redisData = redisDataFromField(field.getField());
            if(redisData != null)
            {
                return redisData;
            }
            List<Order> EntityList = new ArrayList<Order>();
            EntityList = field.getData(orderRepository);
   
            // Cache the value
            serializeOrder(EntityList, field.getField()); 

            return EntityList;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    public Order PostData(Order... Order)
    {
        List<Order> EntityList = new ArrayList<Order>();

        if(EntityList.add(orderRepository.save(Order[0])))
        {
            updateExistingRedisEntry(EntityList);
            return EntityList.get(0);
        }
        return null;
    }
    public Order PutData(Order Order)
    {

        List<Order> temp = new ArrayList<Order>();

        var ExistingOrder = orderRepository.findOrderByOrderId(Order.getOrderId()).get();
        ExistingOrder.setOrderDate(Order.getOrderDate());
        ExistingOrder.setCustomerId(Order.getCustomerId());  
        ExistingOrder.setOrderStatus(Order.getOrderStatus());   
        ExistingOrder.setRequiredDate(Order.getRequiredDate());   
        ExistingOrder.setShippedDate(Order.getShippedDate());   
        ExistingOrder.setStaffId(Order.getStaffId());   
        ExistingOrder.setStoreId(Order.getStoreId());   

        temp.add(ExistingOrder);
        //save updated into in mongo
        orderRepository.save(ExistingOrder);
        //save updated info in redis.
        updateExistingRedisEntry(temp);

        return temp.get(0);
                    
    }
    public ResponseEntity<Boolean> DeleteData(Long id)
    {
        try
        {
            orderRepository.deleteOrderByOrderId(id);
            var orderToDelete = orderRepository.findById(id);
            if(!orderToDelete.isPresent())
            {
                deleteFromExistingRedisEntry(id);
                return ResponseEntity.status(200).body(true);
            }
        }
        catch(Exception e)
        {
            throw new RedisOperationException("",e);
        }
        return ResponseEntity.status(500).body(false);
    }
    
}
