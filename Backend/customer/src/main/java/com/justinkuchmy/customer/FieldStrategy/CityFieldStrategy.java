package com.justinkuchmy.customer.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public class CityFieldStrategy  implements ICustomerFieldStrategy{

     public String Field;
    public CityFieldStrategy(String Field)
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
       EntityList = customerRepository.findCustomersByCity(fieldParams[1]).get();
       return EntityList;
    }
    
}
