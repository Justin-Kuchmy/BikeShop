package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "Order", path = "Order")
public interface OrderRepository extends JpaRepository<Orders, Long>
{
    @Query("SELECT s FROM Orders s WHERE s.customer_id = ?1" ) 
    Optional<List<Orders>> findOrdersByCustomerID(Long id);
}

