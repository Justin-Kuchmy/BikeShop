package com.portfolioprojects.BikeShop.Sales.Entities.OrderItem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository OrderItemRepo;
    public List<Order_items> getOrderItem() {
        return OrderItemRepo.findAll();
    }

    public Order_items getOrderItemById(Long id) {
        return OrderItemRepo.findById(id).get();
    }

    public String addOrderItem(Order_items OrderItem) {
        OrderItemRepo.save(OrderItem);
        return "Order Item " + OrderItem.getOrder_item_id() + " added";
    }

    public int deleteOrderItem(Long id) {
        var found = OrderItemRepo.findById(id);
        if (found.isPresent()) {
            OrderItemRepo.deleteById(id);
        } else {
          throw new IllegalStateException(
            "Order Item with id " + id + " Not Found, Check id"
          );
        }
        return 1;
    }

    public List<Order_items> getOrderItemByOrderId(Long id) {
        return OrderItemRepo.findItemByOrderId(id).get();
    }
}
