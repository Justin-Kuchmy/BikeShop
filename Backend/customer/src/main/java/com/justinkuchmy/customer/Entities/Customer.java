package com.justinkuchmy.customer.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Customers")
public class Customer implements Serializable{
    
    public Customer(String fname, String lname, String phone, String email, String street,String city, String state, String zip)
    {
        this.firstName = fname;
        this.lastName = lname;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zip;
        this.customerId = 0l;
    }
    
    @JsonSerialize(using = CustomObjectIdSerializer.class)
    private ObjectId _id;
    
    private Long customerId;
    private String firstName = null;
    private String lastName = null;
    private String phone = null;
    private String email = null;
    private String street = null;
    private String city = null;
    private String state = null;
    private String zipCode = null;

    @Transient
    private List<Order> customerOrders = new ArrayList<Order>();

    public void AddCustomerOrder(Order order){this.customerOrders.add(order);}



}
