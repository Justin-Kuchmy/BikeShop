package com.justinkuchmy.customer.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public class StateFieldStrategy  implements ICustomerFieldStrategy{

     public String Field;
    public StateFieldStrategy(String Field)
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
       EntityList = customerRepository.findCustomersByState(fieldParams[1]).get();
       return EntityList;
    }
    
}
