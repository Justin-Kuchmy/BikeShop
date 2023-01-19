package com.portfolioprojects.BikeShop.Sales.Entities.Store;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StoreRepoTest {
    @Autowired
    private StoreRepository StoresRepository;
    Stores Stores = new Stores(
        "store_name",
        "phone",
        "email",
        "street",
        "city",
        "state",
        "zip_code"
        );
    @Test
	void StoresRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllStores()
    {
        //given
        var StoresList = StoresRepository.findAll();
        //then
        var size = StoresRepository.count();
        //when
        assertEquals(StoresList.size(), size);
    }
    @Test
    void ShouldFindStoresByEmail()
    {
        //given
        Long firstStoresID = 1L;
        var StoresToFindByEmail = StoresRepository.findById(firstStoresID).get().getEmail();
        var result = StoresRepository.findStoreByEmail(StoresToFindByEmail);
        //then
        assertNotNull(result.get());
        //when
    }
    @Test
    void ShouldAddNewStores()
    {
        //given
        //then
        StoresRepository.save(Stores);
        var StoresInDB = StoresRepository.findStoreByEmail(Stores.getEmail());
        //when
        assertEquals(Stores.getStore_id(), StoresInDB.get().getStore_id());

    }
  
    @Test
    void ShouldUpdateStoresEmail()
    {
        //given
        var StartingStores = StoresRepository.findStoreByEmail(Stores.getEmail());
        //then
        var EmailToUpdate = "newEmail";
        StoresRepository.updateStoreEmail(Long.valueOf(StartingStores.get().getStore_id()), EmailToUpdate);
        //when
        assertNotEquals(StartingStores.get().getEmail(), EmailToUpdate);
    }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long StoresIdToDelete = 4L;
        StoresRepository.save(Stores); //this Stores will be given id 4 automatically. 
        var StartingStores = StoresRepository.findById(StoresIdToDelete);

        //then
        StoresRepository.delete(StartingStores.get());

        //Optional<Stores> will be "Empty" if the Stores wasnt found
        var result = StoresRepository.findById(Long.valueOf(StartingStores.get().getStore_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
