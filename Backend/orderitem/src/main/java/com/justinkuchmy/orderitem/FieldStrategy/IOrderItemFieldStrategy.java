package com.justinkuchmy.orderitem.FieldStrategy;
import java.util.List;

import com.justinkuchmy.orderitem.OrderItemRepository;
import com.justinkuchmy.orderitem.Entities.OrderItem;

public interface IOrderItemFieldStrategy {
    String getField();
    List<OrderItem> getData(OrderItemRepository OrderItemRepository);
}

