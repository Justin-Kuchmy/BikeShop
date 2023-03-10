package com.portfolioprojects.BikeShop.Sales.Entities.OrderItem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/orderitems")
public class OrderItemController {

  @Autowired
  private OrderItemService OrderItemService;

  @GetMapping
  public List<Order_items> getOrderItem() {
    var OrderItemList = OrderItemService.getOrderItem();
    return OrderItemList;
  }

  @GetMapping(path = "OrderItemID/{OrderItemID}")
  public Order_items getOrderItemById(@PathVariable("OrderItemID") Long id) {
    var OrderItemList = OrderItemService.getOrderItemById(id);
    return OrderItemList;
  }

  @GetMapping(path = "OrderID/{OrderID}")
  public Order_itemsResponse getOrderItemByOrderId(@PathVariable("OrderID") Long id)
  {
    return new Order_itemsResponse(OrderItemService.getOrderItemByOrderId(id));    
  }

  @PostMapping
  public String addOrderItem(@RequestBody Order_items OrderItem) {
    String response = OrderItemService.addOrderItem(OrderItem);
    return response;
  }

  @DeleteMapping(path = "{OrderItemID}")
  public int deleteOrderItem(@PathVariable("OrderItemID") Long id) {
    return OrderItemService.deleteOrderItem(id);
  }

}
