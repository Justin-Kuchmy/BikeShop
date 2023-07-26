package com.justinkuchmy.order.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.order.OrderRepository;
import com.justinkuchmy.order.Entities.Order;

public class CustomerIdFieldStrategy  implements IOrderFieldStrategy {
        public String Field;
    public CustomerIdFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<Order> getData(OrderRepository orderRepository) {
       List<Order> EntityList = new ArrayList<Order>();
       var fieldParams = this.Field.split("::");
       EntityList = orderRepository.findOrdersByCustomerId(Long.parseLong(fieldParams[1])).get();
       return EntityList;
    }
}
