package com.portfolioprojects.BikeShop.Sales.Entities.Customer;

import com.portfolioprojects.BikeShop.Sales.Entities.Order.Order_Response;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerConfig config;

  public List<Customers> getCustomers() {
    var customers = customerRepository.findAll();
    return customers;
  }

  public String addcustomer(Customers customer) {
    var found = customerRepository.findCustomerByEmail(customer.getEmail());
    if (found.isPresent()) {
      throw new IllegalStateException("Email Taken");
    } else {
      customerRepository.save(customer);
    }
    return "Customer" + customer.getCustomer_id() + " added";
  }

  public int deletecustomer(Long id) {
    var found = customerRepository.findById(id);
    if (found.isPresent()) {
      customerRepository.deleteById(id);
    } else {
      throw new IllegalStateException(
        "customer with id " + id + " Not Found, Check id"
      );
    }
    return 1;
  }

  public int updatecustomersEmail(Long id, Customers customers) {
    var found = customerRepository.findById(id);
    if (found.isPresent()) {
      customerRepository.updatecustomerEmail(id, customers.getEmail());
    } else {
      throw new IllegalStateException(
        "customer with id " + id + " Not Found, Check id"
      );
    }
    return 1;
  }

  public Customers getCustomersById(Long id) {
    return customerRepository.findById(id).get();
  }

  public List<Customers> findCustomersByName(String FirstName) {
    var response = customerRepository.findCustomersByName(FirstName);
    return response;
  }

  public List<Customers> findCustomersByCity(String City) {
    var response = customerRepository.findCustomersByCity(City);
    return response;
  }

  public List<Customers> findByProp(String prop, String value) {
    List<Customers> response = null;
    
    if (prop.equals("name")) {
      response = customerRepository.findCustomersByName(value.toLowerCase());
    } else if (prop.equals("city")) {
      response = customerRepository.findCustomersByCity(value.toLowerCase());
    }
    return response;
  }

  public Customers getCustomerOrdersByCustomerID(Long id) {
    var Customer = customerRepository.findById(id).get();

    var OrderResponse = config
      .RestTemplate()
      .getForObject(
        "http://localhost:8080/api/v1/orders/customer/items/{id}",
        Order_Response.class,
        id
      );

    if (OrderResponse != null) OrderResponse
      .items()
      .forEach(x -> Customer.getCustomerOrders().add(x));

    return Customer;
  }
}
