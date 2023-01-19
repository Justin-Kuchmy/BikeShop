package com.portfolioprojects.BikeShop.Production.Entities.Brands;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;


import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "brands", path = "brands")
public interface BrandRepository  extends JpaRepository<brands, Long>{

    @Transactional
    @Modifying
    @Query("update brands s set s.brand_name = ?2 WHERE s.brand_id = ?1" ) 
    int UpdateBrandName(Long brandID, String brand_name);

    @Query("select s from brands s where s.brand_name = ?1") 
    Optional<brands> findBrandByName(String brandToFindByName);
}
