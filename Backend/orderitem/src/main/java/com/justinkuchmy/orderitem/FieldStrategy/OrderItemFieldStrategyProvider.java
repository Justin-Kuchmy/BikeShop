package com.justinkuchmy.orderitem.FieldStrategy;

import java.util.HashMap;
import java.util.Map;

public class OrderItemFieldStrategyProvider {
    private final Map<String, IOrderItemFieldStrategy> factoryMap;
    private final String prop;

    public OrderItemFieldStrategyProvider(String Field) {
        this.factoryMap = new HashMap<>();
        this.prop = Field.split("::")[0];
        factoryMap.put("id",new IdFieldStrategy(Field));
        factoryMap.put("all",new AllFieldStrategy(Field));
    }

    public IOrderItemFieldStrategy getStrategy() {
        return factoryMap.get(this.prop);
    }
    
}
