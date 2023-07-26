package com.justinkuchmy.customer.FieldStrategy;
import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public interface ICustomerFieldStrategy {
    String getField();
    List<Customer> getData(CustomerRepository customerRepository);
}

