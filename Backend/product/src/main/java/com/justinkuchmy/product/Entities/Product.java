package com.justinkuchmy.product.Entities;
import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Products")
public class Product implements Serializable {
    public Product(
        String  product_name,
        Long     brand_id,
        Long     category_id,
        Long     model_year,
        double  list_price
    ){
        this.productName = product_name;
        this.brandId = brand_id;
        this.categoryId = category_id;
        this.modelYear = model_year;
        this.listPrice = list_price;
    }
    private Long productId;
    private String  productName = null;
    private Long     brandId;
    private Long     categoryId;
    private Long     modelYear;
    private double  listPrice;
}
