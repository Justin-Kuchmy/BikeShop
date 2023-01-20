package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.portfolioprojects.BikeShop.Sales.Entities.OrderItem.Order_itemsResponse;

@Service
public class OrderService {
    @Autowired
    private OrderRepository OrderRepo;

    @Autowired 
    private RestTemplate restTemplate;

    public List<Orders> getOrder() {
        return OrderRepo.findAll();
    }

    public Orders getOrderById(Long id) {
        var Order = OrderRepo.findById(id).get();
                        
        //instead of injecting the orderItemsService we can do it by using an http request
        //var OrderItems = orderItemsService.getOrderItemByOrderId(id);
        //OrderItems.forEach((x) -> Order.getOrderItems().add(x));

        //In case the request in a different location on the internet we can still get the information we need. 
        var OrderItemsResponse = restTemplate.getForObject(
            "http://localhost:8080/api/v1/orderitems/OrderID/{id}",
            Order_itemsResponse.class,
            id);
            if(OrderItemsResponse != null)
                OrderItemsResponse.items().forEach((x) -> Order.getOrderItems().add(x));

        return Order;
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
