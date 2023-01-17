package com.portfolioprojects.BikeShop.Sales.Entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomersController {

    @Autowired
    private CustomersService customerService;

    @GetMapping
    public List<Customers> getCustomers()
    {
        var customerList = customerService.getCustomers();
        return customerList;
    }

    @GetMapping(path = "id/{customerID}")
    public Customers getCustomerById(@PathVariable("customerID") Long id)
    {
        var customer = customerService.getCustomersById(id);
        return customer;
    }

    @GetMapping(path = "fname/{firstName}")
    public List<Customers> getCustomersByFirstName(@PathVariable("firstName") String fName)
    {
        var customer = customerService.findCustomersByFirstName(fName);
        return customer;
    }

    @PostMapping()
    public String addcustomer(@RequestBody Customers customers)
    {
        System.out.print(customers);
        String response = customerService.addcustomer(customers);
        return response;
    }

    @DeleteMapping(path = "{customersId}")
    public int deletecustomer(@PathVariable("customersId") Long id)
    {
        return customerService.deletecustomer(id);
    }

    @PutMapping(path = "{customersId}")
    public int updatecustomers(@PathVariable("customersId") Long id, @RequestBody Customers customers)
    {
        return customerService.updatecustomersEmail(id, customers);
    }

}
