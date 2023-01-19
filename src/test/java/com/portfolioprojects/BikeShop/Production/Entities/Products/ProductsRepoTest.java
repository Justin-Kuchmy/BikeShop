package com.portfolioprojects.BikeShop.Production.Entities.Products;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductsRepoTest {
    @Autowired
    private ProductRepository ProductRepository;
    products Product = new products(
        "product_name",
        1,
        1,
        2023,
        12.99
        );
    @Test
	void ProductRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllProducts()
    {
        //given
        var ProductList = ProductRepository.findAll();
        //then
        var size = ProductRepository.count();
        //when
        assertEquals(ProductList.size(), size);
    }
    // @Test
    // void ShouldFindProductByEmail()
    // {
    //     //given
    //     Long firstProductID = 1L;
    //     var ProductToFindByEmail = ProductRepository.findById(firstProductID).get().getEmail();
    //     var result = ProductRepository.findProductByEmail(ProductToFindByEmail);
    //     //then
    //     assertNotNull(result.get());
    //     //when
    // }
    // @Test
    // void ShouldAddNewProduct()
    // {
    //     //given
    //     //then
    //     ProductRepository.save(Product);
    //     var ProductInDB = ProductRepository.findProductByEmail(Product.getEmail());
    //     //when
    //     assertEquals(Product.getProduct_id(), ProductInDB.get().getProduct_id());

    // }
  
    // @Test
    // void ShouldUpdateProductsEmail()
    // {
    //     //given
    //     var StartingProduct = ProductRepository.findProductByEmail(Product.getEmail());
    //     //then
    //     var EmailToUpdate = "newEmail";
    //     ProductRepository.updateProductEmail(Long.valueOf(StartingProduct.get().getProduct_id()), EmailToUpdate);
    //     //when
    //     assertNotEquals(StartingProduct.get().getEmail(), EmailToUpdate);
    // }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long ProductIdToDelete = 1446L;
        ProductRepository.save(Product); //this Product will be given id 1446 automatically. 
        var StartingProduct = ProductRepository.findById(ProductIdToDelete);

        //then
        ProductRepository.delete(StartingProduct.get());

        //Optional<Products> will be "Empty" if the Product wasnt found
        var result = ProductRepository.findById(Long.valueOf(StartingProduct.get().getProduct_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
