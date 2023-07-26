package com.justinkuchmy.order.FieldStrategy;
import java.util.List;

import com.justinkuchmy.order.OrderRepository;
import com.justinkuchmy.order.Entities.Order;

public interface IOrderFieldStrategy {
    String getField();
    List<Order> getData(OrderRepository customerRepository);
}

