package com.portfolioprojects.BikeShop.Sales.Entities.Order;
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

public class Orders {
    @Id
    @SequenceGenerator(
        name = "order_sequence",
        sequenceName = "order_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "order_sequence"
    )
    private int order_id;
    private int customer_id;
    private String order_status = null;
    private String order_date = null;
    private String required_date = null;
    private String shipped_date = null;
    private int store_id;
    private int staff_id;  

}
