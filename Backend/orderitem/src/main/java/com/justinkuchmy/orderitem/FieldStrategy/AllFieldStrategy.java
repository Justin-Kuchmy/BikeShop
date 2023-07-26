package com.justinkuchmy.orderitem.FieldStrategy;

import java.util.List;

import com.justinkuchmy.orderitem.OrderItemRepository;
import com.justinkuchmy.orderitem.Entities.OrderItem;

public class AllFieldStrategy  implements IOrderItemFieldStrategy {
    
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
    public List<OrderItem> getData(OrderItemRepository OrderItemRepository) {
       return OrderItemRepository.findAll();
    }
}
