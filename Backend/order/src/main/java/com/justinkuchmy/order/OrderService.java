package com.justinkuchmy.order;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.justinkuchmy.order.Config.RedisDB;
import com.justinkuchmy.order.Entities.Order;
import com.justinkuchmy.order.Entities.Orderitem;
import com.justinkuchmy.order.Entities.WebClientWrapper;
import com.justinkuchmy.order.FieldStrategy.AllFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.CustomerIdFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.IOrderFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.IdFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.OrderFieldStrategyProvider;

@Service
public class OrderService {
    @Autowired
    private RedisDB redisDB;

    @Autowired
    private OrderRepository orderRepository;

    private DiscoveryClient discoveryClient;

    private WebClientWrapper webClient;

    public OrderService(RedisDB redisDB,  DiscoveryClient discoveryClient, WebClientWrapper webClient)
    {
        this.redisDB = redisDB;
        this.discoveryClient = discoveryClient;
        this.webClient = webClient;
        
    }

    public List<Order> getOrders() {
      IOrderFieldStrategy field = new AllFieldStrategy("all");
      var Data = redisDB.GetData(field);
      return Data;
    }

    public List<Order> getOrdersByCustomerId(Long id) {
      IOrderFieldStrategy field = new CustomerIdFieldStrategy("customerid::"+id);
      var data = redisDB.GetData(field);
      return data;
    }

    
    public Order getOrderById(Long id) {
        IOrderFieldStrategy field = new IdFieldStrategy("id::"+id);
        var res = redisDB.GetData(field);
        if(!res.isEmpty())
        {
          return res.get(0);
        }
        return null;
    }

    public List<Order> findOrdersByProp(String prop) {
      OrderFieldStrategyProvider provider = new OrderFieldStrategyProvider(prop);
      IOrderFieldStrategy field = provider.getStrategy();
      var Orders = redisDB.GetData(field);
      return Orders;
  }

    public Order addOrder(Order Order) {   
      var newOrderId = redisDB.getNextOrderItemIdFromRepo();
      Order.setOrderId(newOrderId);
      redisDB.PostData(Order);
      var serviceInstance = getServicePort("orderitem");
      Order.getOrderItems().forEach(orderitem -> {orderitem.setOrderId(newOrderId);});
      var addedOrderItems = webClient.sendPostRequest(serviceInstance, Order.getOrderItems());

      var orders = Order.getOrderItems();

      for(int i = 0; i < orders.size(); i++)
      {
        orders.get(i).setOrderItemId(addedOrderItems.get(i).getOrderItemId());
      };

      return Order;
    }

    public Optional<Boolean> deleteOrder(Long id) {
      IOrderFieldStrategy field = new IdFieldStrategy("id::"+id);
      var data = redisDB.GetData(field);
      if (data.size() >= 1)
       {
          if(redisDB.DeleteData(id).getBody())
              return Optional.of(true);
      } 
      else 
      {
        throw new IllegalStateException(
          "Order with id " + id + " Not Found, Check id"
        );
      }
      return Optional.of(false);
    }

    public ServiceInstance getServicePort(String instance) {
      Random rand = new Random();
      var InstanceList = discoveryClient.getInstances(instance);
      ServiceInstance selectedInstance;
      if (InstanceList.size() != 0) {
        var randIndex = rand.nextInt(InstanceList.size());
        selectedInstance = InstanceList.get(randIndex);
      } else {
        selectedInstance = InstanceList.get(0);
      }
      return selectedInstance;
    }
    public Order updateOrder(Order order) {
      var data = redisDB.PutData(order);
      return data;
    }

}
