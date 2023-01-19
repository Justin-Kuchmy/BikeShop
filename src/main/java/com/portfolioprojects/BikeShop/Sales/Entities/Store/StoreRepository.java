package com.portfolioprojects.BikeShop.Sales.Entities.Store;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "stores", path = "stores")
public interface StoreRepository extends JpaRepository<Stores, Long>
{

    @Modifying
    @Transactional
    @Query("update Stores s set s.email = ?2 WHERE s.store_id = ?1")
    int updateStoreEmail(Long id, String Email);

    @Query("SELECT s FROM Stores s WHERE s.email = ?1" ) 
    Optional<Stores> findStoreByEmail(String email);

    @Query("SELECT s FROM Stores s WHERE s.store_name = ?1" ) 
    List<Stores> findStoresByStoreName(String FirstName);
}