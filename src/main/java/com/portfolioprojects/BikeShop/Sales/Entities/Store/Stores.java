package com.portfolioprojects.BikeShop.Sales.Entities.Store;

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

public class Stores {
    @Id
    @SequenceGenerator(
        name = "store_sequence",
        sequenceName = "store_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "store_sequence"
    )
    private int store_id;
    private String store_name = null;
    private String phone = null;
    private String email = null;
    private String street = null;
    private String city = null;
    private String state = null;
    private String zip_code = null;
}
