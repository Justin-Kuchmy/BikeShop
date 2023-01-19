package com.portfolioprojects.BikeShop.Production.Entities.Products;

import java.util.Optional;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "Product", path = "Product")
public interface ProductRepository  extends JpaRepository<products, Long>{
    
    @Transactional
    @Modifying
    @Query("update products s set s.product_name = ?2 where s.product_id = ?1" ) 
    int UpdateProductName(String ProductName, Long productID);

    @Query("select s from products s where s.product_name = ?1") 
    Optional<products> findProductByName(String ProductToFindByName);
}
