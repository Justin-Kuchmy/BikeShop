package com.justinkuchmy.product.FieldStrategy;

import java.util.ArrayList;
import java.util.List;

import com.justinkuchmy.product.ProductRepository;
import com.justinkuchmy.product.Entities.Product;

public class IdFieldStrategy implements IProductFieldStrategy {

    public String Field;
    public IdFieldStrategy(String Field)
    {
        this.Field = Field;
    }
    @Override
    public String getField() {
        return this.Field;
    }
    @Override
    public List<Product> getData(ProductRepository productRepository) {
        List<Product> EntityList = new ArrayList<Product>();
        var fieldParams = this.Field.split("::");
        var product = productRepository.findProductByProductId(Long.parseLong(fieldParams[1]));
        if(product.isPresent())
        {
            EntityList.add(product.get());
        }
        return EntityList;
       
    }
    
}
