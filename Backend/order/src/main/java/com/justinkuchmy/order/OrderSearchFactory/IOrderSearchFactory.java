package com.justinkuchmy.order.OrderSearchFactory;
import com.justinkuchmy.order.Entities.Order;
import com.justinkuchmy.order.Entities.ListObjectWrapper;

public interface IOrderSearchFactory {
    ListObjectWrapper<Order> search(String value);
}
