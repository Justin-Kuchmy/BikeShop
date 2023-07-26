package com.justinkuchmy.order;

import com.justinkuchmy.order.Entities.ListObjectWrapper;
import com.justinkuchmy.order.Entities.Order;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {

  private OrderService orderService;

  @Autowired
  private DiscoveryClient discoveryClient;

  // @Autowired
  // private WebClient.Builder webClientBuilder;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

  @PostMapping
  public Order addOrder(@RequestBody Order Order) {

    return orderService.addOrder(Order);
   
  }

  @GetMapping(path = "customer/items/{CustomerID}")
  public ListObjectWrapper<Order> getOrdersByCustomerId(@PathVariable("CustomerID") Long id) {
    var orders = orderService.getOrdersByCustomerId(id);
    return new ListObjectWrapper<Order>(orders);
  }

  //getOrderById
  @GetMapping(path = "OrderID/{OrderID}")
  public Order getOrderById(@PathVariable("OrderID") Long id) {
    var order = orderService.getOrderById(id);
    return order;
  }

  @GetMapping(path = "all")
  public ListObjectWrapper<Order> getOrders() {
    var OrderList = orderService.getOrders();
    return new ListObjectWrapper<Order>(OrderList);
  }

  @DeleteMapping("/delete/{idOfItemToDelete}")
  public ResponseEntity<String> deleteOrder(@PathVariable(value = "idOfItemToDelete") Long idOfItemToDelete) {
    orderService.deleteOrder(idOfItemToDelete);
    return ResponseEntity.ok("Order Deleted Successfully");
  }

  public int getServicePort(String instance) {
    Random rand = new Random();
    var InstanceList = discoveryClient.getInstances(instance);
    ServiceInstance selectedInstance;
    if (InstanceList.size() != 0) {
      var randIndex = rand.nextInt(InstanceList.size() - 1);
      selectedInstance = InstanceList.get(randIndex);
    } else {
      selectedInstance = InstanceList.get(0);
    }
    return selectedInstance.getPort();
  }
}
