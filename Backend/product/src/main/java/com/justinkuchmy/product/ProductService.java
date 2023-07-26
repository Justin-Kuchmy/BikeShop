package com.justinkuchmy.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justinkuchmy.product.Config.RedisDB;
import com.justinkuchmy.product.Entities.Product;
import com.justinkuchmy.product.FieldStrategy.AllFieldStrategy;
import com.justinkuchmy.product.FieldStrategy.IProductFieldStrategy;
import com.justinkuchmy.product.FieldStrategy.IdFieldStrategy;
import com.justinkuchmy.product.FieldStrategy.ProductFieldStrategyProvider;

@Service
public class ProductService {

    @Autowired
    private RedisDB redisDB;

    public List<Product> getProducts() {
      IProductFieldStrategy field = new AllFieldStrategy("all");
      var res = redisDB.GetData(field);
      return res;
    }

    public Product getProductsById(Long id) {
        IProductFieldStrategy field = new IdFieldStrategy("id::"+id);
        var res = redisDB.GetData(field);
        if(!res.isEmpty())
        {
          return res.get(0);
        }
        return null;
    }

    public List<Product> findProductsByProp(String prop) {
      ProductFieldStrategyProvider provider = new ProductFieldStrategyProvider(prop);
      IProductFieldStrategy field = provider.getStrategy();
      var Products = redisDB.GetData(field);
      return Products;
  }

}
