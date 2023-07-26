package com.justinkuchmy.orderitem;


import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;

import com.justinkuchmy.orderitem.Config.RedisDB;
import com.justinkuchmy.orderitem.Entities.OrderItem;
import com.justinkuchmy.orderitem.FieldStrategy.AllFieldStrategy;
import com.justinkuchmy.orderitem.FieldStrategy.IOrderItemFieldStrategy;
import com.justinkuchmy.orderitem.FieldStrategy.IdFieldStrategy;
import com.justinkuchmy.orderitem.FieldStrategy.OrderIdFieldStrategy;
import com.justinkuchmy.orderitem.FieldStrategy.OrderItemFieldStrategyProvider;

@Service
public class OrderItemService {

    @Autowired
    private RedisDB redisDB;

    public OrderItemService(RedisDB redisDB)
    {
        this.redisDB = redisDB;
    }

    public List<OrderItem> getOrderItems() {
      IOrderItemFieldStrategy field = new AllFieldStrategy("all");
      var Data = redisDB.GetData(field);
        return Data;

    }

    public OrderItem getOrderItemById(Long id)
    {
        IOrderItemFieldStrategy field = new IdFieldStrategy("id::"+id);
        var data = redisDB.GetData(field).get(0);
        return data;
    }

    public List<OrderItem> getOrderItemByOrderId(Long id) {
      IOrderItemFieldStrategy field = new OrderIdFieldStrategy("orderid::"+id);
      var data = redisDB.GetData(field);
      return data;
    }

    public List<OrderItem> addOrderItem(List<OrderItem> orderItems) {
        Long startingItemId = 0L;
        Long nextOrderItemId = redisDB.getNextOrderItemIdFromRepo();   
        if(orderItems != null)
        {
            for (OrderItem order : orderItems) {
                order.setItemId(++startingItemId);
                order.setOrderItemId(++nextOrderItemId);
            }
            var data = redisDB.PostData(orderItems);
            return data;
        }           
        else
            return null;
        
        
    }



    public List<OrderItem> findOrderItemsByProp(String prop) {
        OrderItemFieldStrategyProvider provider = new OrderItemFieldStrategyProvider(prop);
        IOrderItemFieldStrategy field = provider.getStrategy();
        var OrderItems = redisDB.GetData(field);
        return OrderItems;
    }

    public Optional<Boolean> deleteOrderItem(Long id) {
        IOrderItemFieldStrategy field = new IdFieldStrategy("id::"+id);
        var data = redisDB.GetData(field);
        if (data.size() >= 1) {
            if(redisDB.DeleteData(id).getBody())
                return Optional.of(true);
        } else {
          throw new IllegalStateException(
            "Order Item with id " + id + " Not Found, Check id"
          );
        }
        return Optional.of(false);
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        var data = redisDB.PutData(orderItem);
        return data;
    }

}
