package com.justinkuchmy.product.FieldStrategy;
import java.util.List;

import com.justinkuchmy.product.ProductRepository;
import com.justinkuchmy.product.Entities.Product;

public interface IProductFieldStrategy {
    String getField();
    List<Product> getData(ProductRepository customerRepository);
}

