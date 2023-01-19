package com.portfolioprojects.BikeShop.Production.Entities.Products;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository ProductRepository;

    public List<products> getProducts() {
        var Products = ProductRepository.findAll();
        return Products;
    }

    public products getProductsById(Long id) {

        return ProductRepository.findById(id).get();
    }

    public String addProducts(products Products) {
        var found = ProductRepository.findProductByName(Products.getProduct_name());
        if(found.isPresent())
        {
            throw new IllegalStateException("Product Name Already Exists in DB");
        }
        else
        {
            ProductRepository.save(Products);
        }
        return "Product " + Products.getProduct_id() + " added";
    }

    public int deleteProducts(Long id) {
        var found = ProductRepository.findById(id);
        if(found.isPresent())
        {
            ProductRepository.deleteById(id);
        }
        else
        {
     
            throw new IllegalStateException("Product with id " + id + " not found. Check ID");
        }
        return 1;
    }

    public int updateProductName(Long id, products Products) {
        var found = ProductRepository.findById(id);
        if(found.isPresent())
        {
            ProductRepository.UpdateProductName(Products.getProduct_name(), id);
        }
        else
        {
     
            throw new IllegalStateException("Product with id " + id + " not found. Check ID");
        }
        return 1;

    }
}
