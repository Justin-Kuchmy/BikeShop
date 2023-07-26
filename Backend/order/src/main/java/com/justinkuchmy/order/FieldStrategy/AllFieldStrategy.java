package com.justinkuchmy.order.FieldStrategy;

import java.util.List;

import com.justinkuchmy.order.OrderRepository;
import com.justinkuchmy.order.Entities.Order;

public class AllFieldStrategy  implements IOrderFieldStrategy {
    
    public String Field;
    public AllFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<Order> getData(OrderRepository orderRepository) {
       return orderRepository.findAll();
    }
}
