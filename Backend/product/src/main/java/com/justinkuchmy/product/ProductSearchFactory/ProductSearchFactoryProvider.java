package com.justinkuchmy.product.ProductSearchFactory;

import java.util.HashMap;
import java.util.Map;

import com.justinkuchmy.product.ProductService;

public class ProductSearchFactoryProvider {
    private final Map<String, IProductSearchFactory> factoryMap;

    public ProductSearchFactoryProvider(ProductService customerService) {
        this.factoryMap = new HashMap<>();
    }

    public IProductSearchFactory getFactory(String prop) {
        return factoryMap.get(prop);
    }
    
}
