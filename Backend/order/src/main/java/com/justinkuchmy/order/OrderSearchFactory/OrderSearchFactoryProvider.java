package com.justinkuchmy.order.OrderSearchFactory;

import java.util.HashMap;
import java.util.Map;

import com.justinkuchmy.order.OrderService;

public class OrderSearchFactoryProvider {
    private final Map<String, IOrderSearchFactory> factoryMap;

    public OrderSearchFactoryProvider(OrderService orderService) {
        this.factoryMap = new HashMap<>();
    }

    public IOrderSearchFactory getFactory(String prop) {
        return factoryMap.get(prop);
    }
    
}
