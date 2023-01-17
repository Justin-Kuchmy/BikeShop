package com.portfolioprojects.BikeShop.Production.Entities.Stocks;

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
public class stocks {
    @Id
    @SequenceGenerator(
        name = "stock_sequence",
        sequenceName = "stock_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "stock_sequence"
    )
    private int stock_id;
    private int store_id;
    private int product_id;
    private int quantity;
}
