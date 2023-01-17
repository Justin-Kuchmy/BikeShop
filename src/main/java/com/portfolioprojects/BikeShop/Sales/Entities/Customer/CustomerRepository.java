package com.portfolioprojects.BikeShop.Sales.Entities.Customer;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Repository
@RepositoryRestResource(collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends JpaRepository<Customers, Long>{

    @Modifying //changing the database
    @Transactional
    @Query("update Customers s set s.email = ?2 WHERE s.customer_id = ?1")
    int updatecustomerEmail(Long id, String Email);

    @Query("SELECT s FROM Customers s WHERE s.email = ?1" ) 
    Optional<Customers> findCustomerByEmail(String email);

    @Query("SELECT s FROM Customers s WHERE s.first_name = ?1" ) 
    List<Customers> findCustomersByFirstName(String FirstName);
    
}
