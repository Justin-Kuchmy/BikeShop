package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolioprojects.BikeShop.Sales.Entities.Customer.CustomerRepository;
import com.portfolioprojects.BikeShop.Sales.Entities.OrderItem.Order_itemsResponse;

@Service
public class OrderService {
    @Autowired
    private OrderRepository OrderRepo;
    
    @Autowired 
    private OrderConfig config;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Orders> getOrder() {
        return OrderRepo.findAll();
    }

    public List<Orders> getOrdersByCustomerId(Long id) {
        var customer = customerRepository.findById(id).get();
        var ordersForCustomer = OrderRepo.findOrdersByCustomerID(id);
        for(Orders item : ordersForCustomer.get())
        {
            //customer.getCustomerOrders().add(getOrderById(Long.valueOf(item.getOrder_id())));
           getOrderById(Long.valueOf(item.getOrder_id()));
        }
        return ordersForCustomer.get();
    }
    public Orders getOrderById(Long id) {
       
                        
        //instead of injecting the orderItemsService we can do it by using an http request
        //var OrderItems = orderItemsService.getOrderItemByOrderId(id);
        //OrderItems.forEach((x) -> Order.getOrderItems().add(x));

        //In case the request in a different location on the internet we can still get the information we need. 
        var Order = OrderRepo.findById(id).get();
        var OrderItemsResponse = config.RestTemplate().getForObject(
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
