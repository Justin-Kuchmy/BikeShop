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
    private int staff_id;
    private String first_name = null;
    private String last_name = null;
    private String email = null;
    private String phone = null;
    private Boolean active;
    private int store_id;
    private int manager_id;
}
