package com.justinkuchmy.orderitem.OrderItemSearchFactory;

import java.util.HashMap;
import java.util.Map;

import com.justinkuchmy.orderitem.OrderItemService;

public class OrderItemSearchFactoryProvider {
    private final Map<String, IOrderItemSearchFactory> factoryMap;

    public OrderItemSearchFactoryProvider(OrderItemService orderitemService) {
        this.factoryMap = new HashMap<>();
    }

    public IOrderItemSearchFactory getFactory(String prop) {
        return factoryMap.get(prop);
    }
    
}
