package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository OrderRepo;
    public List<Orders> getOrder() {
        return OrderRepo.findAll();
    }

    public Orders getOrderById(Long id) {
        return OrderRepo.findById(id).get();
    }

    public String addOrder(Orders Order) {
        OrderRepo.save(Order);
    return "Order " + Order.getCustomer_id() + " added";
    }

    public int deleteOrder(Long id) {
        var found = OrderRepo.findById(id);
        if (found.isPresent()) {
            OrderRepo.deleteById(id);
        } else {
          throw new IllegalStateException(
            "Order with id " + id + " Not Found, Check id"
          );
        }
        return 1;
    }
}
