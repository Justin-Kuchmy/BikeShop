package com.portfolioprojects.BikeShop.Sales.Entities.StaffMembers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StaffRepoTest {
    @Autowired
    private StaffRepository StaffRepository;
    Staff Staff = new Staff(
        "first_name",
        "last_name",
        "email",
        "phone",
        true,
        1,
        1
        );
    @Test
	void StaffRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllStaff()
    {
        //given
        var StaffList = StaffRepository.findAll();
        //then
        var size = StaffRepository.count();
        //when
        assertEquals(StaffList.size(), size);
    }
    @Test
    void ShouldFindStaffByEmail()
    {
        //given
        Long firstStaffID = 1L;
        var StaffToFindByEmail = StaffRepository.findById(firstStaffID).get().getEmail();
        var result = StaffRepository.findStaffByEmail(StaffToFindByEmail);
        //then
        assertNotNull(result.get());
        //when
    }
    @Test
    void ShouldAddNewStaff()
    {
        //given
        //then
        StaffRepository.save(Staff);
        var StaffInDB = StaffRepository.findStaffByEmail(Staff.getEmail());
        //when
        assertEquals(Staff.getStaff_id(), StaffInDB.get().getStaff_id());

    }
  
    @Test
    void ShouldUpdateStaffEmail()
    {
        //given
        var StartingStaff = StaffRepository.findStaffByEmail(Staff.getEmail());
        //then
        var EmailToUpdate = "newEmail";
        StaffRepository.updatestaffEmail(Long.valueOf(StartingStaff.get().getStaff_id()), EmailToUpdate);
        //when
        assertNotEquals(StartingStaff.get().getEmail(), EmailToUpdate);
    }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long StaffIdToDelete = 11L;
        StaffRepository.save(Staff); //this Staff will be given id 11 automatically. 
        var StartingStaff = StaffRepository.findById(StaffIdToDelete);

        //then
        StaffRepository.delete(StartingStaff.get());

        //Optional<Staff> will be "Empty" if the Staff wasnt found
        var result = StaffRepository.findById(Long.valueOf(StartingStaff.get().getStaff_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
