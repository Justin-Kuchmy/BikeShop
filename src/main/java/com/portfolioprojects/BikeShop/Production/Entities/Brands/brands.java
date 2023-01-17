package com.portfolioprojects.BikeShop.Production.Entities.Brands;
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

public class brands {
    @Id
    @SequenceGenerator(
        name = "brand_sequence",
        sequenceName = "brand_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "brand_sequence"
    )
    private int brand_id;
    private String brand_name = null;
}
