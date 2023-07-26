package com.justinkuchmy.customer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.justinkuchmy.customer.Config.RedisDB;
import com.justinkuchmy.customer.Config.RedisOperationException;
import com.justinkuchmy.customer.Entities.CustomObjectIdSerializer;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.Entities.Order;
import com.justinkuchmy.customer.Entities.OrderItem;
import com.justinkuchmy.customer.Entities.WebClientWrapper;
import com.justinkuchmy.customer.Entities.Customer;




@SpringBootTest()
class CustomerApplicationTests {

@Mock
private CustomerRepository customerRepository;

@Mock
RedisTemplate<String, String> redisTemplate;


@Mock
private WebClientWrapper webClient;

@Mock
private DiscoveryClient discoveryClient;

@Mock
private HashOperations<String, Object, Object> hashOperations;

@InjectMocks
private CustomerService customerService;

@InjectMocks
private RedisDB redisDB;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        this.webClient = Mockito.mock(WebClientWrapper.class);
        this.customerRepository = Mockito.mock(CustomerRepository.class);
        this.redisDB = new RedisDB(customerRepository, redisTemplate);
        this.customerService = new CustomerService(redisDB, this.discoveryClient, this.webClient);
    }

    List<Customer> getTestData() {
        List<Customer> ExpectedCustomerItemList = new ArrayList<Customer>();
        var customerOne = new Customer("one","two","2262262626","one@Me.com","101 fake st","londonone","MI","12345");
        ExpectedCustomerItemList.add(customerOne);
        var customerTwo = new Customer("three","four","2262267979","two@Me.com","102 fake st","londontwo","TX","12345");
        ExpectedCustomerItemList.add(customerTwo);
        var customerThree = new Customer("five","six","2262261313","three@Me.com","103 fake st","london","TX","12345");
        ExpectedCustomerItemList.add(customerThree);
        var customerFour = new Customer("seven","eight ","2262261414","four@Me.com","104 fake st","london","TX","12345");
        ExpectedCustomerItemList.add(customerFour);
        var customerFive = new Customer("nine","one","2262261515","five@Me.com","105 fake st","london","TX","12345");
        ExpectedCustomerItemList.add(customerFive);

        return ExpectedCustomerItemList;
    }

    List<Customer> getByPropSetUp(List<Customer> customers, String field, List<Customer> filter)
    {
        var fieldParams = field.split("::");
        
        when(customerRepository.findCustomersByCity(fieldParams[1])).thenReturn(Optional.of(filter));
        when(hashOperations.get("customer",field)).thenReturn(ConvertObjectToString(filter));
        return customerService.findCustomersByProp(field);
    }
	@Test
	void contextLoads() {
	}

    @Test
    void testGetCustomers()
    {
        List<Customer> expectedCustomerItemList = getTestData();
        when(customerRepository.findAll()).thenReturn(expectedCustomerItemList);
        when(hashOperations.get("customer", "all")).thenReturn(
            "[{\"customerId\": 0,\"firstName\": \"first\",\"lastName\": \"last\",\"phone\": \"2262262626\",\"email\": \"Me@Me.com\",\"street\": \"132 fake st\",\"city\": \"london\",\"state\": \"TX\",\"zipCode\": \"12345\"},"
            +
            "{\"customerId\": 0,\"firstName\": \"Anotherfirst\",\"lastName\": \"Anotherlast\",\"phone\": \"2262267979\",\"email\": \"Metoo@Me.com\",\"street\": \"123 fake st\",\"city\": \"london\",\"state\": \"TX\",\"zipCode\": \"12345\"}]"
        );

         // call the method to test getting orders from redis
        var actualRedisCustomers = customerService.getCustomers();

        // assert that the returned orders match the expected orders
        assertEquals(expectedCustomerItemList, actualRedisCustomers);

        // mocking no orders in redis
        when(hashOperations.get("customer", "all")).thenReturn(null);

        // will get from mongo instead,
        var actualMongoDBCustomers = customerService.getCustomers();

        assertEquals(expectedCustomerItemList, actualMongoDBCustomers);


    }

   

    @Test
    void testGetCustomersProp()
    {
        var customers = getTestData();
        //mocking "add customer" purpose of setting the id
        for(int i = 0; i < customers.size(); i++)
        {
            Long id = (long) i+1;
            customers.get(i).setCustomerId(id);
        }
        
        List<Customer> CustomersWithSpecificCity = customers.stream().filter(obj -> obj.getCity().equals("london")).collect(Collectors.toList());
        var test1 = getByPropSetUp(customers, "city::london", CustomersWithSpecificCity);
        assertTrue(test1.size() == 3);

        List<Customer> CustomersWithSpecificEmail = customers.stream().filter(obj -> obj.getEmail().equals("one@Me.com")).collect(Collectors.toList());
        var test2 =  getByPropSetUp(customers, "email::one@Me.com", CustomersWithSpecificEmail);
        assertTrue(test2.size() == 1);

        List<Customer> CustomersWithSpecificId = customers.stream().filter(obj -> obj.getCustomerId().equals(1l)).collect(Collectors.toList());
        var test3 =  getByPropSetUp(customers, "id::1", CustomersWithSpecificId);
        assertTrue(test3.size() == 1);

        List<Customer> CustomersWithSpecificState = customers.stream().filter(obj -> obj.getState().equals("TX")).collect(Collectors.toList());
        var test4 =  getByPropSetUp(customers, "state::TX", CustomersWithSpecificState);
        assertTrue(test4.size() == 4);

        //Special case because find by name would check first and last names with 1 given name.

        ///filters just for mock repo and redis Returns
            //filter all customers by first name
            List<Customer> CustomersWithSpecificfName = customers.stream().filter(obj -> obj.getFirstName().equals("one")).collect(Collectors.toList());
            //filter all customers by last name,
            List<Customer> CustomersWithSpecificlName = customers.stream().filter(obj -> obj.getLastName().equals("one")).collect(Collectors.toList());
            //combine
            List<Customer> combinedList = new ArrayList<>();
            combinedList.addAll(CustomersWithSpecificfName);
            combinedList.addAll(CustomersWithSpecificlName);

        when(customerRepository.findByfirstName("one")).thenReturn(Optional.of(CustomersWithSpecificfName));
        when(customerRepository.findBylastName("one")).thenReturn(Optional.of(CustomersWithSpecificlName));
        when(hashOperations.get("customer", "name::one")).thenReturn(ConvertObjectToString(combinedList));

        // customer 1's name is "one two"
        // customer 5's name is "nine one"
        var test5 = customerService.findCustomersByProp("name::one");
        assertTrue(test5.size() == 2);

    }

    @Test
    void testAddCustomer()
    {

        var customerToAdd = new Customer("paul", "French", "226-226-1234", "email@kmail.com", "123 new st", "Flint", "MI", "12345");
        customerToAdd.getCustomerOrders().add(new Order(1l, 3l, "null", "null", "null", "null", 1l, 1l, null));

        

        // Mocking the behavior of DiscoveryClient
        ServiceInstance serviceInstance = new DefaultServiceInstance(
                "mockInstanceId",
                "mockService",
                "localhost",
                8082,
                false);
        
        List<ServiceInstance> mockInstanceList = List.of(serviceInstance);

        when(discoveryClient.getInstances("order")).thenReturn(mockInstanceList);
        when(webClient.sendPostRequest(mockInstanceList.get(0), customerToAdd.getCustomerOrders())).thenReturn(customerToAdd.getCustomerOrders());
        var res = customerService.addCustomer(customerToAdd);
        assertTrue(res != null);
        assertTrue(res.getCustomerId() == 1l);
        assertTrue(res.getCustomerOrders().size() == 1);
    }

    @Test
    void testUpdateCustomer()
    {
        List<Customer> ExpectedCustomerList = getTestData();
        Customer originalCustomer = ExpectedCustomerList.get(0);
        Customer updatedCustomer = originalCustomer;
        updatedCustomer.setCity("Toronto");

        // mock repos for Mongo and Redis interactions
        when(customerRepository.findByCustomerId(originalCustomer.getCustomerId())).thenReturn(Optional.of(originalCustomer));
        when(customerRepository.save(updatedCustomer)).thenReturn(updatedCustomer);

        // call the method to test
        Customer actualUpdatedCustomer = customerService.updateCustomer(updatedCustomer);

        // assert that the returned Customer matches the expected updated Customer
        assertEquals(updatedCustomer, actualUpdatedCustomer);

    }

    @Test
    void testDeleteCustomer()
    {

        var CustomertoDelete = getTestData().get(0);
        Long customerId = 1L;
        CustomertoDelete.setCustomerId(customerId);

        // Adding an Customer item
        when(customerRepository.save(CustomertoDelete)).thenReturn(CustomertoDelete);
        var addedItem = customerRepository.save(CustomertoDelete);
        assertEquals(customerId, addedItem.getCustomerId());

        // Deleting the Customer item
        when(customerRepository.deleteByCustomerId(customerId)).thenReturn(1l);
        Long deleteResult = customerRepository.deleteByCustomerId(customerId);
        assertEquals(1, deleteResult);

        // Verify Deletion
        var deletedItem = customerRepository.findByCustomerId(customerId);
        assertTrue(deletedItem.isEmpty());
    }

    public String ConvertObjectToString(List<Customer> customers)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new SimpleModule().addSerializer(ObjectId.class, new CustomObjectIdSerializer()));
        try {
           String json = mapper.writeValueAsString(customers);
           return json;
        } catch (JsonProcessingException e) {
            throw new RedisOperationException("",e);
        }
    }




}
