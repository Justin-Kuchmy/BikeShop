package com.portfolioprojects.BikeShop.Production.Entities.Stocks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/stocks")
public class StockController {
    @Autowired
    private StockService stocksService;

@GetMapping
public List<stocks> getStocks()
{
		var stocksList = stocksService.getStocks();
        return stocksList;
}

@GetMapping(path = "id/{stocksID}")
public stocks getStockById(@PathVariable("stocksID") Long id)
{
		var stocksList = stocksService.getStockById(id);
        return stocksList;
}

@PutMapping(path = "{stocksID}")
public int UpdateItemQuantity(@PathVariable("stocksID") Long id, @RequestBody stocks stocks)
{
	return stocksService.UpdateItemQuantity(id, stocks);
}
}
