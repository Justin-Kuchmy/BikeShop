package com.justinkuchmy.product.FieldStrategy;

import java.util.HashMap;
import java.util.Map;

public class ProductFieldStrategyProvider {
    private final Map<String, IProductFieldStrategy> factoryMap;
    private final String prop;

    public ProductFieldStrategyProvider(String Field) {
        this.factoryMap = new HashMap<>();
        this.prop = Field.split("::")[0];
        factoryMap.put("id",new IdFieldStrategy(Field));
        factoryMap.put("all",new AllFieldStrategy(Field));
    }

    public IProductFieldStrategy getStrategy() {
        return factoryMap.get(this.prop);
    }
    
}
