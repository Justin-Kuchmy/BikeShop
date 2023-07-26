package com.justinkuchmy.customer.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.customer.CustomerRepository;
import com.justinkuchmy.customer.Entities.Customer;

public class NameFieldStrategy  implements ICustomerFieldStrategy {
        public String Field;
    public NameFieldStrategy(String Field)
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
       var firstNameData = customerRepository.findByfirstName(fieldParams[1]);
       var firstLastData = customerRepository.findBylastName(fieldParams[1]);
       if(firstNameData.get().get(0).getCustomerId() != firstLastData.get().get(0).getCustomerId())
       {
        firstNameData.get().forEach(customer -> {EntityList.add(customer);});
        firstLastData.get().forEach(customer -> {EntityList.add(customer);});
       }
       return EntityList;
    }
}
