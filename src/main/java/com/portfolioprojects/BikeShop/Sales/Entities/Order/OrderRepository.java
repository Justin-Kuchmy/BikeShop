package com.portfolioprojects.BikeShop.Sales.Entities.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "Order", path = "Order")
public interface OrderRepository extends JpaRepository<Orders, Long>
{
    
}

