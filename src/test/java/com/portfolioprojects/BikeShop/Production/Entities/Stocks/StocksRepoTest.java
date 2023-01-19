package com.portfolioprojects.BikeShop.Production.Entities.Stocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StocksRepoTest {
    @Autowired
    private StockRepository StockRepository;
    stocks Stock = new stocks(
            1,
            1,
            10
        );
    @Test
	void StockRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllStocks()
    {
        //given
        var StockList = StockRepository.findAll();
        //then
        var size = StockRepository.count();
        //when
        assertEquals(StockList.size(), size);
    }

    // @Test
    // void ShouldUpdateItemQuantity()
    // {
    //     //given
    //     var StartingStock = StockRepository.UpdateItemQuantity(10, null)
    //     //then
    //     var EmailToUpdate = "newEmail";
    //     StockRepository.updateStockEmail(Long.valueOf(StartingStock.get().getStock_id()), EmailToUpdate);
    //     //when
    //     assertNotEquals(StartingStock.get().getEmail(), EmailToUpdate);
    // }

    

   

    

    
}
