package com.portfolioprojects.BikeShop.Sales.Entities.StaffMembers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/staffmembers")
public class StaffMembersController {

    @Autowired
    private StaffService staffService;

  @GetMapping
  public List<Staff> getStaff() {
    var staffList = staffService.getStaff();
    return staffList;
  }

  @GetMapping(path = "id/{StaffID}")
  public Staff getStaffById(@PathVariable("StaffID") Long id) {
    var staffList = staffService.getStaffById(id);
    return staffList;
  }

  @PostMapping
  public String addStaff(@RequestBody Staff Staff) 
  {
    String response = staffService.addstaff(Staff);
    return response;
  }

  @DeleteMapping(path = "{StaffID}")
  public int deleteStaff(@PathVariable("StaffID") Long id) {
    return staffService.deletestaff(id);
  }

  @PutMapping(path = "{StaffID}")
  public int updateStaff(@PathVariable("StaffID") Long id,@RequestBody Staff Staff) {
    var result = staffService.updateStaffEmail(id, Staff);
    return result;
  }
}
