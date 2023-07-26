package com.justinkuchmy.customer.FieldStrategy;

import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public class AllFieldStrategy  implements ICustomerFieldStrategy {
    
    public String Field;
    public AllFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<Customer> getData(CustomerRepository customerRepository) {
       return customerRepository.findAll();
    }
}
