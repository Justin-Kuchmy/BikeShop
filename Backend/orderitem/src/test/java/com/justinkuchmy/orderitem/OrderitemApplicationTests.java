package com.justinkuchmy.orderitem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.cache.CacheProperties.Redis;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import com.justinkuchmy.orderitem.Config.RedisDB;
import com.justinkuchmy.orderitem.Entities.OrderItem;
import com.justinkuchmy.orderitem.FieldStrategy.AllFieldStrategy;
import com.justinkuchmy.orderitem.FieldStrategy.IOrderItemFieldStrategy;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@SpringBootTest
class OrderitemApplicationTests {

@Mock
private OrderItemRepository orderItemRepository;

@Mock
RedisTemplate<String, String> redisTemplate;

@Mock
private HashOperations<String, Object, Object> hashOperations;

@InjectMocks
private OrderItemService orderItemService;

@InjectMocks
private RedisDB redisDB;

private OrderItemController orderItemController;


@BeforeEach
void setUp() {
	MockitoAnnotations.openMocks(this);

	when(redisTemplate.opsForHash()).thenReturn(hashOperations);
	this.redisDB = new RedisDB(orderItemRepository, redisTemplate);
	this.orderItemService = new OrderItemService(redisDB);
}

public List<OrderItem> getTestData()
{
	List<OrderItem> ExpectedorderItemList = new ArrayList<OrderItem>();
	OrderItem orderOne = new OrderItem(1L, 1L, 1L, 20L, 599.99, 0.2);
	OrderItem orderTwo = new OrderItem(1L, 2L, 2L, 10L, 599.99, 0.2);
	ExpectedorderItemList.add(orderOne);
	ExpectedorderItemList.add(orderTwo);
	return ExpectedorderItemList;
}

@Test 
void addOrderItem()
{

	List<OrderItem> orderItemList = new ArrayList<>();
	OrderItem orderOne = new OrderItem(3l,1l,1l,20l,599.99,0.2);
	OrderItem orderTwo = new OrderItem(3l,1l,1l,20l,599.99,0.2);
	orderItemList.add(orderOne);
	orderItemList.add(orderTwo);
	Long mockRepoCount = 1000l;
	when(orderItemRepository.count()).thenReturn(mockRepoCount++);

	when(orderItemRepository.saveAll(orderItemList)).thenReturn(orderItemList);
	

	var AddedOrderItems = orderItemService.addOrderItem(orderItemList);
	assertTrue(AddedOrderItems != null);

	var FailedToAdd = orderItemService.addOrderItem(null);
	assertTrue(FailedToAdd == null);

}


@Test 
void getorderItems()
{
	//Starting Test Data
	List<OrderItem> orderItemList = getTestData();
	when(orderItemRepository.findAll()).thenReturn(orderItemList);
	when(orderItemRepository.saveAll(orderItemList)).thenReturn(orderItemList);
	when(hashOperations.get("orderitem", "all")).thenReturn("[{\"orderItemId\":2,\"orderId\":1,\"itemId\":1,\"productId\":1,\"quantity\":20,\"listPrice\":599.99,\"discount\":0.2},{\"orderItemId\":3,\"orderId\":1,\"itemId\":2,\"productId\":2,\"quantity\":10,\"listPrice\":599.99,\"discount\":0.2}]");
	var repoItems = orderItemRepository.findAll();
	var redisCacheItems = orderItemService.getOrderItems();

	assertTrue(repoItems != null);
	assertTrue(redisCacheItems != null);
	assertTrue(repoItems.size() == redisCacheItems.size());
}


	@Test 
	void getOrderItemByOrderId()
	{
		List<OrderItem> orderItemList = getTestData();
		orderItemList.add(new OrderItem(2L, 1L, 1L, 20L, 599.99, 0.2)); //adding a 3rd item with a different orderID
		when(orderItemRepository.findAll()).thenReturn(orderItemList);
		when(orderItemRepository.findItemByOrderId(1l)).thenReturn(Optional.of(orderItemList));

		var AllItemsFromRepo = orderItemController.getOrderItemByOrderId(1l).getObjectList();
		List<OrderItem> ItemsWithSpecificOrderID = AllItemsFromRepo.stream()
                                   .filter(obj -> obj.getOrderId().equals(1l))
                                   .collect(Collectors.toList());

		//assert that num of items created in method == num of items from repo
		assertTrue(orderItemList.size() == AllItemsFromRepo.size());

		//assert that it filters itemsfromrepo to match given order_id 
		//in this case 2 out of the 3 orders have order_id 1 and the other is order_id 2
		assertTrue(ItemsWithSpecificOrderID.size() == 2);

	}
	

	
    @Test 
    void updateOrderItem()
    {
		List<OrderItem> orderItemList = getTestData();
        when(orderItemRepository.saveAll(orderItemList)).thenReturn(orderItemList);
        when(orderItemRepository.save(orderItemList.get(0))).thenReturn(orderItemList.get(0));
        when(orderItemRepository.findOrderItemByorderItemId(2l)).thenReturn(Optional.of(orderItemList.get(0)));

		 //adding items to repo.
        var success = orderItemService.addOrderItem(orderItemList);
		assertNotNull(success);

		//get an order item from repo/redis
		var originalItem = orderItemService.getOrderItemById(2l);
		var originalQuantity = originalItem.getQuantity();

		//change the quantity property
		originalItem.setQuantity(25l); 

		//update the repo/redis with the change
		var updatedItem = orderItemService.updateOrderItem(originalItem);
		var updatedQuantity = updatedItem.getQuantity();
       
		//check that the quantity changed after being updated in the repo/redis
        assertTrue(originalQuantity != updatedQuantity);

    }

	@Test 
	void deleteOrderItem()
	{
		// Create an example order item and its ID
		OrderItem orderItem = new OrderItem(3l,1l,1l,20l,599.99,0.2);
        Long orderItemId = 1L;
		orderItem.setOrderItemId(orderItemId);
        
        //Adding an order item
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);
        var addedItem = orderItemRepository.save(orderItem);
        assertEquals(orderItemId, addedItem.getOrderItemId());
        
        //Deleting the order item
        when(orderItemRepository.deleteOrderItemByorderItemId(orderItemId)).thenReturn(1l);
        Long deleteResult = orderItemRepository.deleteOrderItemByorderItemId(orderItemId);
        assertEquals(1, deleteResult);
        
        // Verify Deletion
        var deletedItem = orderItemRepository.findOrderItemByorderItemId(orderItemId);
        assertTrue(deletedItem.isEmpty());

	}
    
	@Test
	void contextLoads() {
	}


}
