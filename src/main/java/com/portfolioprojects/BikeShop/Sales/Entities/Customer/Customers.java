package com.portfolioprojects.BikeShop.Sales.Entities.Customer;
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
 
public class Customers {
    @Id
    @SequenceGenerator(
        name = "customer_sequence",
        sequenceName = "customer_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "customer_sequence"
    )
    private int customer_id;
    private String first_name = null;
    private String last_name = null;
    private String phone = null;
    private String email = null;
    private String street = null;
    private String city = null;
    private String state = null;
    private String zip_code = null;
}
