package com.justinkuchmy.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justinkuchmy.product.Entities.ListObjectWrapper;
import com.justinkuchmy.product.Entities.Product;
import com.justinkuchmy.product.ProductSearchFactory.IProductSearchFactory;
import com.justinkuchmy.product.ProductSearchFactory.ProductSearchFactoryProvider;


@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {

  @Autowired
  private ProductService productsService;

  @GetMapping(path = "all")
  public ListObjectWrapper<Product> getproducts() {

    var productsList = productsService.getProducts();
    return new ListObjectWrapper<Product>(productsList);
  }

  @GetMapping(path = "id/{productsID}")
  public Product getproductsById(@PathVariable("productsID") Long id) {
    var productsList = productsService.getProductsById(id);
    return productsList;
  }

  @GetMapping(path = "search")
  public ResponseEntity<ListObjectWrapper<Product>> filterByProp(@RequestParam String prop, @RequestParam String value)
  {
     ProductSearchFactoryProvider factoryProvider = new ProductSearchFactoryProvider(productsService);
        IProductSearchFactory searchFactory = factoryProvider.getFactory(prop);
        if (searchFactory != null) {
            ListObjectWrapper<Product> result = searchFactory.search(prop+"::"+value);
            // Return the result as needed
            return ResponseEntity.ok(result);
        } else {
            // Handle the case when prop is not recognized (e.g., throw an exception or return an error response)
            return ResponseEntity.badRequest().build();
        }
  }

}
