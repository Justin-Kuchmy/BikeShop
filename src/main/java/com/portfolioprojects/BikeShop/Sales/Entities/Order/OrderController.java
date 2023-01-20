package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolioprojects.BikeShop.Sales.Entities.OrderItem.OrderItemService;

@RestController
@RequestMapping(path = "api/v1/orders")
public class OrderController {

  @Autowired
  private OrderService OrderService;

  @Autowired
  private OrderItemService orderItemsService;

  @GetMapping
  public List<Orders> getOrder() {
    var OrderList = OrderService.getOrder();
    return OrderList;
  }

  @GetMapping(path = "id/{OrderID}")
  public Orders getOrderById(@PathVariable("OrderID") Long id) {
    var Order = OrderService.getOrderById(id);
    return Order;
  }

  @GetMapping(path = "Items/{OrderID}")
  public Orders getOrderWithItems(@PathVariable("OrderID") Long id)
  {
    var Order = OrderService.getOrderById(id);
    var OrderItems = orderItemsService.getOrderItemByOrderId(id);
    OrderItems.forEach((x) -> Order.getOrderItems().add(x));
    return Order;
  }

  @PostMapping
  public String addOrder(@RequestBody Orders Order) {
    String response = OrderService.addOrder(Order);
    return response;
  }

  @DeleteMapping(path = "{OrderID}")
  public int deleteOrder(@PathVariable("OrdersID") Long id) {
    return OrderService.deleteOrder(id);
  }

}
