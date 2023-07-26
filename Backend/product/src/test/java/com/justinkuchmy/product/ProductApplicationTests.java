package com.justinkuchmy.product;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.springframework.beans.factory.annotation.Autowired;

// import com.justinkuchmy.product.Entities.Product;

@SpringBootTest
class ProductApplicationTests {

    // @Autowired
    // private ProductRepository productRepository;

    // @Autowired
    // private ProductController productController;

    // @Test 
	// void getProducts()
	// {
    //     var expected = productController.getproducts().getObjectList();
    //     var actual = productRepository.findAll();
    //     assertEquals(expected, actual);
	// }
	// @Test 
	// void getProductById()
	// {
    //     var expected = productController.getproductsById(1L);
    //     var actual = productRepository.findById(1L).get();
    //     assertEquals(expected, actual);
	// }

    // @Test
    // void getProductNameById()
    // {
    //     var expected = productController.getProductNameById(1L);
    //     var actual = "Trek 820 - 2016";
    //     assertEquals(expected, actual);
    // }

	// @Test 
	// void addProduct()
	// {
    //     Product product = new Product("new item",9,6,2016,379.99);
    //     var expected = productController.addproducts(product);
    //     assertTrue(expected.getProduct_id() > 0);
	// }
    // @Test 
	// void updateProducts()
	// {
    //     Product product = new Product("Trek 820 - 3016",9,6,2016,379.99);
    //     productController.updateproducts(1l, product);
    //     var newProduct = productController.getproductsById(1l);
    //     boolean result = product.getProduct_name() == newProduct.getProduct_name();
    //     assertTrue(result);
	// }
	// @Test 
	// void deleteProducts()
	// {
    //     var productToDelete = productController.getproductsById(1l);
    //     Long idOfItemToDelete = (long) productToDelete.getProduct_id();
    //     var result = productController.deleteproducts(idOfItemToDelete);
    //     assertTrue(result == 1);
    //     var afterDelete = "";
    //     try
    //     {
    //         productController.getproductsById(1l);
    //     }
    //     catch(Exception ex)
    //     {
    //         afterDelete = "null";
    //     }
    //     finally
    //     {
    //         assertTrue(afterDelete == "null");
    //     }
        
        
        
        
	// }

    
	@Test
	void contextLoads() {
	}

}
