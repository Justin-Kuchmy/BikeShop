package com.portfolioprojects.BikeShop.Sales.Entities.Store;

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

@RestController
@RequestMapping(path = "api/v1/Stores")
public class StoreController {

    @Autowired
    private StoreService StoresService;

  @GetMapping
  public List<Stores> getStores() {
    var StoresList = StoresService.getStore();
    return StoresList;
  }

  @GetMapping(path = "id/{StoresID}")
  public Stores getStoresById(@PathVariable("StoresID") Long id) {
    var StoresList = StoresService.getStoreById(id);
    return StoresList;
  }

  @PostMapping
  public String addStores(@RequestBody Stores Stores) 
  {
    String response = StoresService.addStore(Stores);
    return response;
  }

  @DeleteMapping(path = "{StoresID}")
  public int deleteStores(@PathVariable("StoresID") Long id) {
    return StoresService.deleteStore(id);
  }

  @PutMapping(path = "{StoresID}")
  public int updateStores(@PathVariable("StoresID") Long id,@RequestBody Stores Stores) {
    var result = StoresService.updateStoreEmail(id, Stores);
    return result;
  }
}
