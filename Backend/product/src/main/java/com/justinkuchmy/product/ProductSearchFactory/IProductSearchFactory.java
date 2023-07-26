package com.justinkuchmy.product.ProductSearchFactory;
import com.justinkuchmy.product.Entities.ListObjectWrapper;
import com.justinkuchmy.product.Entities.Product;

public interface IProductSearchFactory {
   ListObjectWrapper<Product> search(String value);
}
