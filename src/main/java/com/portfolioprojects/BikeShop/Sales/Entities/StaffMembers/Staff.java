package com.portfolioprojects.BikeShop.Sales.Entities.StaffMembers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table

public class Staff {
    public Staff(){};
    public Staff(String fname, String lname, String email, String phone, Boolean active, int store_id, int manager_id) {
              this.first_name = fname;
              this.last_name = lname;
              this.email = email;
              this.phone = phone;
              this.active = active;
              this.store_id = store_id;
              this.manager_id = manager_id;
    }
    @Id
    @SequenceGenerator(
        name = "staff_sequence",
        sequenceName = "staff_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "staff_sequence"
    )
    private int     staff_id;
    private String  first_name = null;
    private String  last_name = null;
    private String  email = null;
    private String  phone = null;
    private Boolean active;
    private int     store_id;
    private int     manager_id;
}
