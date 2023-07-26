package com.justinkuchmy.customer.Config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.justinkuchmy.customer.Entities.CustomObjectIdSerializer;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.FieldStrategy.ICustomerFieldStrategy;


@Component
public class RedisDB {

    private CustomerRepository customerRepository;

    private RedisTemplate<String, String> redisTemplate;
        
    public RedisDB(CustomerRepository customerRepository, RedisTemplate<String, String> redisTemplate)
    {
        this.customerRepository = customerRepository;
        this.redisTemplate = redisTemplate;

    }
    
    public Long getNextOrderItemIdFromRepo()
    {
        return this.customerRepository.count()+1;
    }

    public List<Customer> redisDataFromField(String Field)
    {
        var cachedValue = redisTemplate.opsForHash().get("customer",Field);
            if (cachedValue != null) {
                if(!cachedValue.equals("null"))
                {
                    return parseCachedValue(cachedValue.toString());
                }
            }
            return null;
    }
    private List<Customer> parseCachedValue(String cachedValue)
    {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            var customerArray = objectMapper.readValue(cachedValue, Customer[].class);
            List<Customer> customerList = new ArrayList<>();
            for (Customer customer : customerArray) {
                customerList.add(customer);
            }
            return customerList;
        } catch (Exception e)
        {
            throw new RedisOperationException("",e);
        }
    }
    private String serializeCustomer(List<Customer> CustomerData, String Field)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(ObjectId.class, new CustomObjectIdSerializer()));
        try {
            String json = mapper.writeValueAsString(CustomerData);
            redisTemplate.opsForHash().put("customer",Field, json);
            return json.toString();
        } catch (JsonProcessingException e) {
            throw new RedisOperationException("",e);
        }
    }
    private List<Customer> getOrCreateOriginalList() {
        var existingCache = redisTemplate.opsForHash().get("Customer", "all");
        if (existingCache == null) {
            return customerRepository.findAll();
        } else {
            return parseCachedValue(existingCache.toString());
        }
    }
    
    private void updateExistingRedisEntry(List<Customer> NewData)
    {
        List<Customer> originalList =  getOrCreateOriginalList();
        originalList.addAll(NewData);
        putToHash(originalList);

    }
    private void deleteFromExistingRedisEntry(Long CustomerIdToDelete)
    {
        //get all Customer Data
        List<Customer> originalList = getOrCreateOriginalList();
        originalList.removeIf(Customer -> Customer.getCustomerId().equals(CustomerIdToDelete));
        putToHash(originalList);
       
    }

    private void putToHash(List<Customer> originalList)
    {
        redisTemplate.opsForHash().put("Customer","all", serializeCustomer(originalList, "all"));
        redisTemplate.expire("Customer", Duration.ofSeconds(3600));
    }
    public ResponseEntity<Boolean> DeleteData(Long id)
    {
        try
        {
            customerRepository.deleteByCustomerId(id);
            var CustomerToDelete = customerRepository.findById(id);
            if(!CustomerToDelete.isPresent())
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
    public Customer PutData(String Field, Customer customer)
    {
        List<Customer> temp = new ArrayList<Customer>();

        var ExistingCustomer = customerRepository.findByCustomerId(customer.getCustomerId()).get();
        ExistingCustomer.setFirstName(customer.getFirstName());
        ExistingCustomer.setLastName(customer.getLastName());
        ExistingCustomer.setEmail(customer.getEmail());
        ExistingCustomer.setPhone(customer.getPhone());
        ExistingCustomer.setStreet(customer.getStreet());
        ExistingCustomer.setCity(customer.getCity());
        ExistingCustomer.setState(customer.getState());
        ExistingCustomer.setZipCode(customer.getZipCode());

        temp.add(ExistingCustomer);
        //save updated into in mongo
        customerRepository.save(ExistingCustomer);
        //save updated info in redis.
        updateExistingRedisEntry(temp);
        return temp.get(0);
    }

    public List<Customer> GetData(ICustomerFieldStrategy field, Customer... customer)
    {
        try {
            List<Customer> redisData = redisDataFromField(field.getField());
            if(redisData != null)
            {
                return redisData;
            }
            List<Customer> EntityList = new ArrayList<Customer>();
            EntityList = field.getData(customerRepository);
   
            // Cache the value
            serializeCustomer(EntityList, field.getField()); 

            return EntityList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Customer PostData(String Field, Customer... customer)
    {
        List<Customer> EntityList = new ArrayList<Customer>();
        try
        {
            if(EntityList.add(customerRepository.save(customer[0])))
            {
                updateExistingRedisEntry(EntityList);
                return EntityList.get(0);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new RedisOperationException("",e);
        }
 
    }

}
