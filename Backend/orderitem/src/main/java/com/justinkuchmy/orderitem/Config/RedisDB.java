package com.justinkuchmy.orderitem.Config;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.justinkuchmy.orderitem.Entities.OrderItem;
import com.justinkuchmy.orderitem.FieldStrategy.IOrderItemFieldStrategy;
import com.justinkuchmy.orderitem.OrderItemRepository;
import com.justinkuchmy.orderitem.Entities.CustomObjectIdSerializer;

@Component
public class RedisDB {


    private OrderItemRepository orderItemRepository;

    private RedisTemplate<String, String> redisTemplate;

    public RedisDB(OrderItemRepository orderItemRepository, RedisTemplate<String, String> redisTemplate)
    {
        this.orderItemRepository = orderItemRepository;
        this.redisTemplate = redisTemplate;

    }

    public Long getNextOrderItemIdFromRepo()
    {
        return this.orderItemRepository.count()+1;
    }
    
    private List<OrderItem> redisDataFromField(String field)
    {
        
        try
        {
            var cachedValue = redisTemplate.opsForHash().get("orderitem",field);
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
    private List<OrderItem> parseCachedValue(String cachedValue)
    {
         try {
            ObjectMapper objectMapper = new ObjectMapper();
            var OrderItemArray = objectMapper.readValue(cachedValue, OrderItem[].class);
            List<OrderItem> OrderItemList = new ArrayList<>();
            for (OrderItem OrderItem : OrderItemArray) {
                OrderItemList.add(OrderItem);
            }
            return OrderItemList;
        } catch (Exception e)
        {
            throw new RedisOperationException("Error while parsing cached value", e);
        }
    }
       private String serializeOrderItem(List<OrderItem> OrderItemData, String field)
    {
         ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(ObjectId.class, new CustomObjectIdSerializer()));
        try {
            String json = mapper.writeValueAsString(OrderItemData);

            // Store the JSON string in Redis
            redisTemplate.opsForHash().put("orderitem", field, json);
            redisTemplate.expire("orderitem", Duration.ofSeconds(3600));
            return json.toString();
        } catch (JsonProcessingException e) {
            throw new RedisOperationException("Error Trying to Map Object To Json.",e);
        }
    }
    private List<OrderItem> getOrCreateOriginalList() {
        var existingCache = redisTemplate.opsForHash().get("orderitem", "all");
        if (existingCache == null) {
            return orderItemRepository.findAll();
        } else {
            return parseCachedValue(existingCache.toString());
        }
    }
    
    private void updateExistingRedisEntry(List<OrderItem> NewData)
    {
        List<OrderItem> originalList =  getOrCreateOriginalList();
        originalList.addAll(NewData);
        putToHash(originalList);

    }
    private void deleteFromExistingRedisEntry(Long orderItemIdToDelete)
    {
        //get all orderItem Data
        List<OrderItem> originalList = getOrCreateOriginalList();
        originalList.removeIf(orderItem -> orderItem.getOrderItemId().equals(orderItemIdToDelete));
        putToHash(originalList);
       
    }

    private void putToHash(List<OrderItem> originalList)
    {
        redisTemplate.opsForHash().put("orderitem","all", serializeOrderItem(originalList, "all"));
        redisTemplate.expire("orderitem", Duration.ofSeconds(3600));
    }

    public ResponseEntity<Boolean> DeleteData(Long id)
    {
        try
        {
            orderItemRepository.deleteOrderItemByorderItemId(id);
            var OrderItemToDelete = orderItemRepository.findOrderItemByorderItemId(id);
            if(!OrderItemToDelete.isPresent())
            {
                deleteFromExistingRedisEntry(id);
                
                return ResponseEntity.status(200).body(true);
            }
        }
        catch(Exception e)
        {
            throw new RedisOperationException("Error in Deletion",e);
        }
        return ResponseEntity.status(500).body(false);
    }
    public OrderItem PutData( OrderItem OrderItem)
    {
        List<OrderItem> temp = new ArrayList<OrderItem>();

        var ExistingOrderItem = orderItemRepository.findOrderItemByorderItemId(OrderItem.getOrderItemId()).get();
        ExistingOrderItem.setDiscount(OrderItem.getDiscount());
        ExistingOrderItem.setListPrice(OrderItem.getListPrice());
        ExistingOrderItem.setQuantity(OrderItem.getQuantity());

        temp.add(ExistingOrderItem);
        //save updated into in mongo
        orderItemRepository.save(ExistingOrderItem);
        //save updated info in redis.
        updateExistingRedisEntry(temp);

        return temp.get(0);
    }
    public List<OrderItem> GetData(IOrderItemFieldStrategy field, OrderItem... OrderItem)
    {
        try {
            var redisData = redisDataFromField(field.getField());
            if(redisData != null)
            {
                return redisData;
            }
            List<OrderItem> EntityList = new ArrayList<OrderItem>();
            EntityList = field.getData(orderItemRepository);
   
            // Cache the value
            serializeOrderItem(EntityList, field.getField()); 

            return EntityList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RedisOperationException("Error Getting Data", e);
        }
    }
    public List<OrderItem> PostData(List<OrderItem> orderItems)
    {
        List<OrderItem> savedOrderItems = new ArrayList<OrderItem>();
        try
        {
            Iterable<OrderItem> savedEntities = orderItemRepository.saveAll(orderItems);
            savedEntities.forEach(savedOrderItems::add);

            updateExistingRedisEntry(savedOrderItems);
            return savedOrderItems;
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
        
    }

}
