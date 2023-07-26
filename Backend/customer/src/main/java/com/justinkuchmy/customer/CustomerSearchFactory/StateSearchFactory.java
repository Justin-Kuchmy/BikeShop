package com.justinkuchmy.customer.CustomerSearchFactory;

import com.justinkuchmy.customer.CustomerService;
import com.justinkuchmy.customer.Entities.Customer;
import com.justinkuchmy.customer.Entities.ListObjectWrapper;

public class StateSearchFactory implements ICustomerSearchFactory {

    private final CustomerService customerService;

    public StateSearchFactory(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public ListObjectWrapper<Customer> search(String value) {
        return new ListObjectWrapper<Customer>(customerService.findCustomersByProp(value));
    }
    
}
