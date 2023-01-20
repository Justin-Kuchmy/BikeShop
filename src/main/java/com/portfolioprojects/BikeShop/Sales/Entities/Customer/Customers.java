package com.portfolioprojects.BikeShop.Sales.Entities.Customer;
import java.util.List;

import com.portfolioprojects.BikeShop.Sales.Entities.Order.Orders;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
@Data
@Entity
@Table
 
public class Customers {
    public Customers(){}
    public Customers( String fname, String lname, String phone, String email, String street,
            String city, String state, String zip) 
            {
                this.first_name = fname;
                this.last_name = lname;
                this.phone = phone;
                this.email = email;
                this.street = street;
                this.state = state;
                this.zip_code = zip;
            }
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
    
    @Transient
    private List<Orders> customerOrders;
}
