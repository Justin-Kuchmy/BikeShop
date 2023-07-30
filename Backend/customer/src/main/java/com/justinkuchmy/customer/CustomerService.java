package com.justinkuchmy.customer;

import com.justinkuchmy.customer.Config.RedisDB;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.Entities.Order;
import com.justinkuchmy.customer.Entities.WebClientWrapper;
import com.justinkuchmy.customer.FieldStrategy.*;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerService {

    @Autowired
    public CustomerConfig config;

    private RedisDB redisDB;

    private WebClientWrapper webClient;

    private DiscoveryClient discoveryClient;

       public CustomerService(RedisDB redisDB,  DiscoveryClient discoveryClient,  WebClientWrapper webClientBuilder) {
        this.redisDB = redisDB;
        this.discoveryClient = discoveryClient;
        this.webClient = webClientBuilder;
    }


    public List<Customer> getCustomers() {
        ICustomerFieldStrategy field = new AllFieldStrategy("all");
        List<Customer> customers = redisDB.GetData(field);
        return customers;

    }

    public Customer addCustomer(Customer customer) {
        var newCustomerId = redisDB.getNextOrderItemIdFromRepo();
        customer.setCustomerId(newCustomerId);
        redisDB.PostData(customer);
        var serviceInstance = getServicePort("order");
        customer.getCustomerOrders().forEach(order -> {order.setCustomerId(newCustomerId);});
        var addedOrder = webClient.sendPostRequest(serviceInstance, customer.getCustomerOrders().get(0));
        customer.getCustomerOrders().clear();
        customer.getCustomerOrders().add(addedOrder);
        return customer;

       

    }

    public Customer getCustomersById(Long id, String path) {
        ICustomerFieldStrategy field = new IdFieldStrategy(path + id);
        var customer = redisDB.GetData(field);
        if (customer != null) {
            return customer.get(0);
        } else
            return null;
    }

    public Customer getCustomerByEmail(String email, String path) {
        ICustomerFieldStrategy field = new EmailFieldStrategy(path + email);
        var customer = redisDB.GetData(field);
        if (customer != null) {
            // Return 200 OK with data in the response body
            return customer.get(0);
        } else {
            // Return 404 Not Found without a response body
            return null;
        }
    }

    public List<Customer> findCustomersByProp(String prop) {
        CustomerFieldStrategyProvider provider = new CustomerFieldStrategyProvider(prop);
        ICustomerFieldStrategy field = provider.getStrategy();
        var customers = redisDB.GetData(field);
        return customers;
    }

    public Customer updateCustomer(Customer customer) {
        var found = redisDB.PutData("update", customer);
        if (found != null) {
            return found;
        } else {
            return null;
        }

    }

    public Boolean deleteCustomer(Long id) {
        var deleteSuccess = redisDB.DeleteData(id).getBody();
        if (deleteSuccess) {
            return true;
        } else {
            return false;
        }
    }

    public ServiceInstance getServicePort(String instance) {
    Random rand = new Random();
    var InstanceList = discoveryClient.getInstances(instance);
    ServiceInstance selectedInstance;
    if (InstanceList.size() != 0) {
      var randIndex = rand.nextInt(InstanceList.size());
      selectedInstance = InstanceList.get(randIndex);
    } else {
      selectedInstance = InstanceList.get(0);
    }
    return selectedInstance;
  }

}
