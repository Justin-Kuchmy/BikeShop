package com.justinkuchmy.orderitem.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.orderitem.OrderItemRepository;
import com.justinkuchmy.orderitem.Entities.OrderItem;

public class OrderIdFieldStrategy  implements IOrderItemFieldStrategy {
        public String Field;
    public OrderIdFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<OrderItem> getData(OrderItemRepository orderRepository) {
       List<OrderItem> EntityList = new ArrayList<OrderItem>();
       var fieldParams = this.Field.split("::");
       EntityList = orderRepository.findItemByOrderId(Long.parseLong(fieldParams[1])).get();
       return EntityList;
    }
}
