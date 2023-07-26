package com.justinkuchmy.order;
import com.justinkuchmy.order.Entities.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {
    Optional<List<Order>> findOrdersByCustomerId(Long id);
    Optional<Order> findOrderByOrderId(Long id);
    Long deleteOrderByOrderId(Long orderId);
}
