package com.portfolioprojects.BikeShop.Production.Entities.Products;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productsService;

@GetMapping
public List<products> getproducts()
{
		var productsList = productsService.getProducts();
        return productsList;
}

@GetMapping(path = "id/{productsID}")
public products getproductsById(@PathVariable("productsID") Long id)
{
		var productsList  = productsService.getProductsById(id);
        return productsList;
}

@PostMapping()
public String addproducts(@RequestBody products products)
{
        String response = productsService.addProducts(products);
        return response;
}

@DeleteMapping(path = "{productsID}")
public int deleteproducts(@PathVariable("productssId") Long id)
{
  return productsService.deleteProducts(id);
}

@PutMapping(path = "{productsID}")
public int updateproducts(@PathVariable("productsID") Long id, @RequestBody products products)
{
	return productsService.updateProductName(id, products);
}
}
