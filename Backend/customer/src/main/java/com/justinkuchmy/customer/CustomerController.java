package com.justinkuchmy.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.customer.CustomerSearchFactory.CustomerSearchFactoryProvider;
import com.justinkuchmy.customer.CustomerSearchFactory.ICustomerSearchFactory;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.Entities.ListObjectWrapper;

import java.util.Random;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    @PostMapping()
    public  ResponseEntity<Customer> addcustomer(@RequestBody Customer customer)
    {
        var newlyAddedCustomer = customerService.addCustomer(customer);
        return  ResponseEntity.ok(newlyAddedCustomer);
    }

   @GetMapping(path = "id/{customerID}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerID") Long id)
    {
        var customer = customerService.getCustomersById(id, "id::");
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping(path = "email/{customer_Email}")
    public ResponseEntity<Customer>  getCustomerByEmail(@PathVariable("customer_Email") String email)
    {
        var customer = customerService.getCustomerByEmail(email, "email::");
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }



    @GetMapping(path = "all")
    public ResponseEntity<ListObjectWrapper<Customer>> getCustomers()
    {
        var list = customerService.getCustomers();
        if(list != null)
        {
            return ResponseEntity.ok(new ListObjectWrapper<Customer>(list));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
    }

    @GetMapping(path = "search")
    public ResponseEntity<ListObjectWrapper<Customer>> getCustomerByProp(@RequestParam String prop, @RequestParam String value) { 
        CustomerSearchFactoryProvider factoryProvider = new CustomerSearchFactoryProvider(customerService);
        ICustomerSearchFactory searchFactory = factoryProvider.getFactory(prop);
        if (searchFactory != null) {
            ListObjectWrapper<Customer> result = searchFactory.search(prop+"::"+value);
            // Return the result as needed
            return ResponseEntity.ok(result);
        } else {
            // Handle the case when prop is not recognized (e.g., throw an exception or return an error response)
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping(path = "/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer Customer) {
        //todo
        return ResponseEntity.ok(customerService.updateCustomer(Customer));

    }
    @DeleteMapping(path = "delete/{idOfItemToDelete}")
    public ResponseEntity<Boolean>  deleteCustomer(@PathVariable("idOfItemToDelete") Long idOfItemToDelete) {
        var res = customerService.deleteCustomer(idOfItemToDelete); 
        return ResponseEntity.ok(res);
    }
}
