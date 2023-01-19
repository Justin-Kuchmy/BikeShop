package com.portfolioprojects.BikeShop.Production.Entities.Products;import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table

public class products {
    public products(){}
    public products(
        String  product_name,
        int     brand_id,
        int     category_id,
        int     model_year,
        double  list_price
    ){
        this.product_name = product_name;
        this.brand_id = brand_id;
        this.category_id = category_id;
        this.model_year = model_year;
        this.list_price = list_price;
    }
    @Id
    @SequenceGenerator(
        name = "product_sequence",
        sequenceName = "product_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "product_sequence"
    )
    private int product_id;
    private String  product_name = null;
    private int     brand_id;
    private int     category_id;
    private int     model_year;
    private double  list_price;
}
