package com.justinkuchmy.product.FieldStrategy;

import java.util.List;

import com.justinkuchmy.product.ProductRepository;
import com.justinkuchmy.product.Entities.Product;

public class AllFieldStrategy implements IProductFieldStrategy {

    public String Field;
    public AllFieldStrategy(String Field)
    {
        this.Field = Field;
    }
    @Override
    public String getField() {
        return this.Field;
    }
    @Override
    public List<Product> getData(ProductRepository productRepository) {
       return productRepository.findAll();
    }
    
}
