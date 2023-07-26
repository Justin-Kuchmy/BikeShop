package com.justinkuchmy.customer.FieldStrategy;

import java.util.HashMap;
import java.util.Map;

public class CustomerFieldStrategyProvider {
    private final Map<String, ICustomerFieldStrategy> factoryMap;
    private final String prop;

    public CustomerFieldStrategyProvider(String Field) {
        this.factoryMap = new HashMap<>();
        this.prop = Field.split("::")[0];
        factoryMap.put("id",new IdFieldStrategy(Field));
        factoryMap.put("name",new NameFieldStrategy(Field));
        factoryMap.put("all",new AllFieldStrategy(Field));
        factoryMap.put("email", new EmailFieldStrategy(Field));
        factoryMap.put("city", new CityFieldStrategy(Field));
        factoryMap.put("state", new StateFieldStrategy(Field));
    }

    public ICustomerFieldStrategy getStrategy() {
        return factoryMap.get(this.prop);
    }
    
}
