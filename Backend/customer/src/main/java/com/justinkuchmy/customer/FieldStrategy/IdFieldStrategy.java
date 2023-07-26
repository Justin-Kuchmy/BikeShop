package com.justinkuchmy.customer.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public class IdFieldStrategy  implements ICustomerFieldStrategy {
        public String Field;
    public IdFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<Customer> getData(CustomerRepository customerRepository) {
       List<Customer> EntityList = new ArrayList<Customer>();
       var fieldParams = this.Field.split("::");
       EntityList.add(customerRepository.findByCustomerId(Long.parseLong(fieldParams[1])).get());
       return EntityList;
    }
}
