package com.portfolioprojects.BikeShop.Sales.Entities.OrderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "Order_items", path = "Order_items")
public interface OrderItemRepository extends JpaRepository<Order_items, Long>{}


