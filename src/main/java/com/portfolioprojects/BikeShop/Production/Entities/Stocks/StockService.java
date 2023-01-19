package com.portfolioprojects.BikeShop.Production.Entities.Stocks;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private StockRepository StockRepository;

    public List<stocks> getStocks() {
        var Stocks = StockRepository.findAll();
        return Stocks;
    }

    public stocks getStockById(Long Id) {
        return StockRepository.findById(Id).get();
    }

    public int UpdateItemQuantity(Long id, stocks Stocks) {
        var found = StockRepository.findById(id);
        if(found.isPresent())
        {
            StockRepository.UpdateItemQuantity(Stocks.getQuantity(), id);
        }
        else
        {
            throw new IllegalStateException("Stock with id " + id + " not found. Check ID");
        }
        return 1;

    }
}
