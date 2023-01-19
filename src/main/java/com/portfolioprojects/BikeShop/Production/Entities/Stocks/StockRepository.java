package com.portfolioprojects.BikeShop.Production.Entities.Stocks;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "stocks", path = "stocks")
public interface StockRepository extends JpaRepository<stocks, Long> {
    @Transactional
    @Modifying
    @Query("update stocks s set s.quantity = ?1 where s.product_id = ?2" ) 
    int UpdateItemQuantity(int quantity, Long productID);
}
