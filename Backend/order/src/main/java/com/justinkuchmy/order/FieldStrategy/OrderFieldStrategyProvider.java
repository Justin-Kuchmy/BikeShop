package com.justinkuchmy.order.FieldStrategy;

import java.util.HashMap;
import java.util.Map;

public class OrderFieldStrategyProvider {
    private final Map<String, IOrderFieldStrategy> factoryMap;
    private final String prop;

    public OrderFieldStrategyProvider(String Field) {
        this.factoryMap = new HashMap<>();
        this.prop = Field.split("::")[0];
        //factoryMap.put("id",new IdFieldStrategy(Field));
        //factoryMap.put("all",new AllFieldStrategy(Field));
    }

    public IOrderFieldStrategy getStrategy() {
        return factoryMap.get(this.prop);
    }
    
}
