package com.portfolioprojects.BikeShop.Production.Entities.Categories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.portfolioprojects.BikeShop.Sales.Entities.Customer.Customers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "categories", path = "categories")
public interface CategoryRepository  extends JpaRepository<categories, Long>{
    
    @Transactional
    @Modifying
    @Query("update categories s set s.category_name = ?2 WHERE s.category_id = ?1" ) 
    int UpdateCategoryName(String categoryName, Long categoryId);

    @Query("select s from categories s where s.category_name = ?1") 
    Optional<categories> findCategoryByName(String CategoryToFindByName);
}
