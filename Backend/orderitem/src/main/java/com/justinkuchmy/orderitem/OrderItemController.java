package com.justinkuchmy.orderitem;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.orderitem.Entities.ListObjectWrapper;
import com.justinkuchmy.orderitem.Entities.OrderItem;

@RestController
@RequestMapping(path = "api/v1/orderitem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemServ)
    {
        this.orderItemService = orderItemServ;
    }

    @PostMapping
    public List<OrderItem> addOrderItem(@RequestBody List<OrderItem> orderItems) {
        var list = orderItemService.addOrderItem(orderItems);
        return list;
    }


    @GetMapping(path = "orderid/{orderid}") 
    public ListObjectWrapper<OrderItem> getOrderItemByOrderId(@PathVariable("orderid") Long id)
    {
        var res = orderItemService.getOrderItemByOrderId(id); 
        return new ListObjectWrapper<OrderItem>(res);
    }

    @GetMapping(path = "all")
    public ListObjectWrapper<OrderItem> getOrderItems()
    {
        var OrderitemList = orderItemService.getOrderItems();
        return new ListObjectWrapper<OrderItem>(OrderitemList);
    }

    @GetMapping(path = "id/{orderitemid}")     
    public OrderItem getByOrderItemID(@PathVariable("orderitemid") long orderItemID) {
        return orderItemService.getOrderItemById(orderItemID);
    }

    @DeleteMapping(path = "delete/{orderitemid}") 
    public Boolean DeleteOrderItemByID(@PathVariable("orderitemid") long OrderItemID)
    {
        var res = orderItemService.deleteOrderItem(OrderItemID);
        if(res.isPresent())
        {
            return res.get();
        }
        return false;
    }
}
