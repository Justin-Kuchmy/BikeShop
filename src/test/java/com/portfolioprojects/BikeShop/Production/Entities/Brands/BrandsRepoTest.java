package com.portfolioprojects.BikeShop.Production.Entities.Brands;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BrandsRepoTest {
    @Autowired
    private BrandRepository BrandRepository;
    brands Brand = new brands(
            "brand_name"
        );
    @Test
	void BrandRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllBrands()
    {
        //given
        var BrandList = BrandRepository.findAll();
        //then
        var size = BrandRepository.count();
        //when
        assertEquals(BrandList.size(), size);
    }
    @Test
    void ShouldFindBrandByName()
    {
        //given
        Long firstBrandID = 1L;
        var BrandToFindByName = BrandRepository.findById(firstBrandID).get().getBrand_name();
        var result = BrandRepository.findBrandByName(BrandToFindByName);
        //then
        assertNotNull(result.get());
        //when
    }
    @Test
    void ShouldAddNewBrand()
    {
        //given
        //then
        BrandRepository.save(Brand);
        var BrandInDB = BrandRepository.findBrandByName(Brand.getBrand_name());
        //when
        assertEquals(Brand.getBrand_id(), BrandInDB.get().getBrand_id());

    }
  
    @Test
    void ShouldUpdateBrandName()
    {
        //given
        //BrandRepository.save(Brand);
        var StartingBrand = BrandRepository.findBrandByName(Brand.getBrand_name());
        //then
        String NameToUpdate = "newBrandName";
        Long brandID = Long.valueOf(StartingBrand.get().getBrand_id());
        BrandRepository.UpdateBrandName(brandID, NameToUpdate);
        //when
        assertNotEquals(StartingBrand.get().getBrand_name(), NameToUpdate);
    }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long BrandIdToDelete = 10L;
        BrandRepository.save(Brand); //this Brand will be given id 10 automatically. 
        var StartingBrand = BrandRepository.findById(BrandIdToDelete);

        //then
        BrandRepository.delete(StartingBrand.get());

        //Optional<Brands> will be "Empty" if the Brand wasnt found
        var result = BrandRepository.findById(Long.valueOf(StartingBrand.get().getBrand_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
