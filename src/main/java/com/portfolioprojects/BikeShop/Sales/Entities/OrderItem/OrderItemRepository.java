package com.portfolioprojects.BikeShop.Sales.Entities.OrderItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "Order_items", path = "Order_items")
public interface OrderItemRepository extends JpaRepository<Order_items, Long>{

    @Query("SELECT s FROM Order_items s WHERE s.order_id = ?1" ) 
    Optional<List<Order_items>> findItemByOrderId(Long id);
}


