package com.portfolioprojects.BikeShop.Production.Entities.Brands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<brands> getbrands() {
        var brands = brandRepository.findAll();
        return brands;
    }

    public brands getbrandsById(Long id) {

        return brandRepository.findById(id).get();
    }

    public String addbrands(brands brands) {
        var found = brandRepository.findBrandByName(brands.getBrand_name());
        if(found.isPresent())
        {
            throw new IllegalStateException("Brand Name Already Exists in DB");
        }
        else
        {
            brandRepository.save(brands);
        }
        return "Brand " + brands.getBrand_id() + " added";
    }

    public int deletebrands(Long id) {
        var found = brandRepository.findById(id);
        if(found.isPresent())
        {
            brandRepository.deleteById(id);
        }
        else
        {
     
            throw new IllegalStateException("Brand with id " + id + " not found. Check ID");
        }
        return 1;
    }

    public int updateBrandName(Long id, brands brands) {
        var found = brandRepository.findById(id);
        if(found.isPresent())
        {
            brandRepository.UpdateBrandName(id, brands.getBrand_name());
        }
        else
        {
     
            throw new IllegalStateException("Brand with id " + id + " not found. Check ID");
        }
        return 1;

    }
}
