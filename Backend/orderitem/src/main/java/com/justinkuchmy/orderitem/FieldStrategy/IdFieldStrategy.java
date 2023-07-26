package com.justinkuchmy.orderitem.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.orderitem.OrderItemRepository;
import com.justinkuchmy.orderitem.Entities.OrderItem;

public class IdFieldStrategy  implements IOrderItemFieldStrategy {
        public String Field;
    public IdFieldStrategy(String Field)
    {
        this.Field = Field;
    }

    @Override
    public String getField() {
        return this.Field;
    }

    @Override
    public List<OrderItem> getData(OrderItemRepository OrderItemRepository) {
       List<OrderItem> EntityList = new ArrayList<OrderItem>();
       var fieldParams = this.Field.split("::");
       EntityList.add(OrderItemRepository.findOrderItemByorderItemId(Long.parseLong(fieldParams[1])).get());
       return EntityList;
    }
}
