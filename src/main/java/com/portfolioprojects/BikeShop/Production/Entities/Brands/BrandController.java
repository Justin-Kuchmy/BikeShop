package com.portfolioprojects.BikeShop.Production.Entities.Brands;

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
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;
  @GetMapping
  public List<brands> getbrands() {
    var brandsList = brandService.getbrands();
    return brandsList;
  }

  @GetMapping(path = "id/{brandsID}")
  public brands getbrandsById(@PathVariable("brandsID") Long id) {
    var brandsList = brandService.getbrandsById(id);
    return brandsList;
  }

  @PostMapping
  public String addbrands(@RequestBody brands brands) {
    String response = brandService.addbrands(brands);
    return response;
  }

  @DeleteMapping(path = "{brandsID}")
  public int deletebrands(@PathVariable("brandssId") Long id) {
    return brandService.deletebrands(id);
  }

  @PutMapping(path = "{brandsID}")
  public int updatebrands(
    @PathVariable("brandsID") Long id,
    @RequestBody brands brands
  ) {
    return brandService.updateBrandName(id, brands);
  }
}
