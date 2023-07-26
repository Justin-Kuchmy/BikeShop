package com.justinkuchmy.orderitem.OrderItemSearchFactory;
import com.justinkuchmy.orderitem.Entities.OrderItem;
import com.justinkuchmy.orderitem.Entities.ListObjectWrapper;

public interface IOrderItemSearchFactory {
    ListObjectWrapper<OrderItem> search(String value);
}
