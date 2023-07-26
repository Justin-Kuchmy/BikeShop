package com.justinkuchmy.orderitem;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.justinkuchmy.orderitem.Entities.OrderItem;


@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, Long> {
    Optional<List<OrderItem>> findItemByOrderId(Long id);
    Optional<OrderItem> findOrderItemByorderItemId(Long id);
    Long deleteOrderItemByorderItemId(Long id);
}
