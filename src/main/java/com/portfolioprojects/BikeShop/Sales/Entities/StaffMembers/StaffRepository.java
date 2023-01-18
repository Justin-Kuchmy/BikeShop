package com.portfolioprojects.BikeShop.Sales.Entities.StaffMembers;

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
@RepositoryRestResource(collectionResourceRel = "staff", path = "staff")
public interface StaffRepository extends JpaRepository<Staff, Long>
{

    @Modifying
    @Transactional
    @Query("update Staff s set s.email = ?2 WHERE s.staff_id = ?1")
    int updatestaffEmail(Long id, String Email);

    @Query("SELECT s FROM Staff s WHERE s.email = ?1" ) 
    Optional<Staff> findStaffByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.first_name = ?1" ) 
    List<Staff> findStaffByFirstName(String FirstName);
}