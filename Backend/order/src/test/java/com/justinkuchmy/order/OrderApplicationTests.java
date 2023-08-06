package com.justinkuchmy.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;

import com.justinkuchmy.order.Config.RedisDB;
import com.justinkuchmy.order.Entities.ListObjectWrapper;
import com.justinkuchmy.order.Entities.Order;
import com.justinkuchmy.order.Entities.Orderitem;
import com.justinkuchmy.order.Entities.WebClientWrapper;
import com.justinkuchmy.order.FieldStrategy.CustomerIdFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.IOrderFieldStrategy;
import com.justinkuchmy.order.FieldStrategy.IdFieldStrategy;

import reactor.core.publisher.Mono;

@SpringBootTest
class OrderApplicationTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    RedisTemplate<String, String> redisTemplate;

    @Mock
    WebClientWrapper webClient;

    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @Mock
    private DiscoveryClient discoveryClient;

    @InjectMocks
    private OrderService orderService;

    @InjectMocks
    private RedisDB redisDB;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
        this.webClient = Mockito.mock(WebClientWrapper.class);
        this.orderRepository = Mockito.mock(OrderRepository.class);
        this.redisDB = new RedisDB(this.orderRepository, this.redisTemplate);
        this.orderService = new OrderService(this.redisDB, this.discoveryClient, this.webClient);

    }

    @Test
    void contextLoads() {
    }

    public List<Order> getTestData() {
        List<Order> ExpectedorderItemList = new ArrayList<Order>();
        ExpectedorderItemList.add(new Order(1l, "1", "1991-12-21", "1991-12-21", "1991-12-21", 1l, 1l));
        ExpectedorderItemList.add(new Order(1l, "1", "1991-12-21", "1991-12-21", "1991-12-21", 1l, 1l));
        ExpectedorderItemList.add(new Order(2l, "1", "1991-12-21", "1991-12-21", "1991-12-21", 1l, 1l));
        ExpectedorderItemList.get(0).getOrderItems().add(new Orderitem(1l, 1l, 1l, 2l, 29.99, 0));
        ExpectedorderItemList.get(0).getOrderItems().add(new Orderitem(1l, 2l, 1l, 2l, 29.99, 0));
        ExpectedorderItemList.get(0).getOrderItems().add(new Orderitem(1l, 3l, 1l, 2l, 29.99, 0));
        return ExpectedorderItemList;
    }

    @Test
    void getorders() {
        // Starting Test Data
        List<Order> ExpectedOrderList = getTestData();

        // mock repos for Mongo and Redis interactions
        when(orderRepository.findAll()).thenReturn(ExpectedOrderList);
        when(hashOperations.get("order", "all")).thenReturn(ExpectedOrderList.toString());

        // call the method to test getting orders from redis
        var actualRedisOrders = orderService.getOrders();

        // assert that the returned orders match the expected orders
        assertEquals(ExpectedOrderList, actualRedisOrders);

        // mocking no orders in redis
        when(hashOperations.get("order", "all")).thenReturn(null);

        // will get from mongo instead,
        var actualMongoDBOrders = orderService.getOrders();

        assertEquals(ExpectedOrderList, actualMongoDBOrders);
    }

    @Test
    void getOrdersById() {
        var ExpectedOrderList = getTestData();

        when(orderRepository.findAll()).thenReturn(ExpectedOrderList);
        when(hashOperations.get("order", "id::3")).thenReturn(
                "[{\"orderId\": 3,\"customerId\": 2,\"orderStatus\": \"1\",\"orderDate\": \"1991-12-21\",\"requiredDate\": \"1991-12-21\",\"shippedDate\":  \"1991-12-21\",\"storeId\": 1,\"staffId\": 1}]");
        var actualWithSpecificId = orderService.getOrderById(3l);

        assertEquals(ExpectedOrderList.size(), orderService.getOrders().size());
        assertTrue(actualWithSpecificId.getOrderId() == 3l);

    }

    @Test
    void getOrderByCustomerId() {

        // Starting Test Data
        List<Order> ExpectedOrderList = getTestData();
        when(orderRepository.findAll()).thenReturn(ExpectedOrderList);

        // call the method to test
        var AllItemsFromRepo = orderService.getOrders();
        List<Order> OrderWithSpecificCustomerID = AllItemsFromRepo.stream()
                .filter(obj -> obj.getCustomerId().equals(1l))
                .collect(Collectors.toList());

        // assert that num of items created in method == num of items from repo
        assertTrue(ExpectedOrderList.size() == AllItemsFromRepo.size());

        // assert that it filters itemsfromrepo to match given order_id
        // in this case 2 out of the 3 orders have order_id 1 and the other is order_id
        // 2
        assertTrue(OrderWithSpecificCustomerID.size() == 2);

    }

    @Test
    void addOrder() {
        var orderToAdd = getTestData().get(0);
        // Mocking the behavior of DiscoveryClient
        ServiceInstance serviceInstance = new DefaultServiceInstance(
                "mockInstanceId",
                "mockService",
                "localhost",
                8083,
                false);

        List<ServiceInstance> mockInstanceList = List.of(serviceInstance);

        when(discoveryClient.getInstances("orderitem")).thenReturn(mockInstanceList);
        when(webClient.sendPostRequest(mockInstanceList.get(0), orderToAdd.getOrderItems())).thenReturn(orderToAdd.getOrderItems());
        var res = orderService.addOrder(orderToAdd);
        assertTrue(res != null);
        assertTrue(res.getOrderId() == 1l);
        assertTrue(res.getOrderItems().size() == 3);
        

    }

    @Test
    void updateorder() {
        List<Order> ExpectedOrderList = getTestData();
        Order originalOrder = ExpectedOrderList.get(0);
        Order updatedOrder = originalOrder;
        updatedOrder.setOrderStatus("updated status");

        // mock repos for Mongo and Redis interactions
        when(orderRepository.findOrderByOrderId(originalOrder.getOrderId())).thenReturn(Optional.of(originalOrder));
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        // call the method to test
        Order actualUpdatedOrder = orderService.updateOrder(updatedOrder);

        // assert that the returned order matches the expected updated order
        assertEquals(updatedOrder, actualUpdatedOrder);

    }

    @Test
    void deleteorder() {
        // Create an example order item and its ID

        var ordertoDelete = getTestData().get(0);
        Long orderId = 1L;
        ordertoDelete.setOrderId(orderId);

        // Adding an order item
        when(orderRepository.save(ordertoDelete)).thenReturn(ordertoDelete);
        var addedItem = orderRepository.save(ordertoDelete);
        assertEquals(orderId, addedItem.getOrderId());

        // Deleting the order item
        when(orderRepository.deleteOrderByOrderId(orderId)).thenReturn(1l);
        Long deleteResult = orderRepository.deleteOrderByOrderId(orderId);
        assertEquals(1, deleteResult);

        // Verify Deletion
        var deletedItem = orderRepository.findOrderByOrderId(orderId);
        assertTrue(deletedItem.isEmpty());

    }

}
