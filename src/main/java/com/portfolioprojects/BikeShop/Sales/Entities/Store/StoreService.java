package com.portfolioprojects.BikeShop.Sales.Entities.Store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

    @Autowired
    private StoreRepository StoreRepository;
    public List<Stores> getStore() {
        return StoreRepository.findAll();
    }

    public Stores getStoreById(Long id) {
        return StoreRepository.findById(id).get();
    }

    public String addStore(Stores Store) {
        var found = StoreRepository.findStoreByEmail(Store.getEmail());
    if (found.isPresent()) {
      throw new IllegalStateException("Email Taken");
    } else {
      StoreRepository.save(Store);
    }
    return "Store" + Store.getStore_id() + " added";
    }

    public int deleteStore(Long id) {
        var found = StoreRepository.findById(id);
        if (found.isPresent()) {
          StoreRepository.deleteById(id);
        } else {
          throw new IllegalStateException(
            "Store with id " + id + " Not Found, Check id"
          );
        }
        return 1;
    }

    public int updateStoreEmail(Long id, Stores Store) 
    {
        var found = StoreRepository.findById(id);
        if (found.isPresent()) 
        {
            StoreRepository.updateStoreEmail(id, Store.getEmail());
        } 
        else 
        {
            throw new IllegalStateException("Store with id " + id + " Not Found, Check id");
        }
        return 1;
    }
    
}
