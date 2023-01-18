package com.portfolioprojects.BikeShop.Sales.Entities.StaffMembers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;
    public List<Staff> getStaff() {
        return staffRepository.findAll();
    }

    public Staff getStaffById(Long id) {
        return staffRepository.findById(id).get();
    }

    public String addstaff(Staff staff) {
        var found = staffRepository.findStaffByEmail(staff.getEmail());
    if (found.isPresent()) {
      throw new IllegalStateException("Email Taken");
    } else {
      staffRepository.save(staff);
    }
    return "staff" + staff.getStaff_id() + " added";
    }

    public int deletestaff(Long id) {
        var found = staffRepository.findById(id);
        if (found.isPresent()) {
          staffRepository.deleteById(id);
        } else {
          throw new IllegalStateException(
            "staff with id " + id + " Not Found, Check id"
          );
        }
        return 1;
    }

    public int updateStaffEmail(Long id, Staff staff) 
    {
        var found = staffRepository.findById(id);
        if (found.isPresent()) 
        {
            staffRepository.updatestaffEmail(id, staff.getEmail());
        } 
        else 
        {
            throw new IllegalStateException("staff with id " + id + " Not Found, Check id");
        }
        return 1;
    }
    
}
