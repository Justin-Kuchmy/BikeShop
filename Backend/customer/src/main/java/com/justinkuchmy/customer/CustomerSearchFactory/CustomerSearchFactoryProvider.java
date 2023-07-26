package com.justinkuchmy.customer.CustomerSearchFactory;

import java.util.HashMap;
import java.util.Map;

import com.justinkuchmy.customer.CustomerService;

public class CustomerSearchFactoryProvider {
    private final Map<String, ICustomerSearchFactory> factoryMap;

    public CustomerSearchFactoryProvider(CustomerService customerService) {
        this.factoryMap = new HashMap<>();
        factoryMap.put("name", new NameSearchFactory(customerService));
        factoryMap.put("city", new CitySearchFactory(customerService));
        factoryMap.put("state", new StateSearchFactory(customerService));
    }

    public ICustomerSearchFactory getFactory(String prop) {
        return factoryMap.get(prop);
    }
    
}
