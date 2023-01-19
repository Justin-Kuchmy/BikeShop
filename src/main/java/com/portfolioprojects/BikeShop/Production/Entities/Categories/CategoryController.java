package com.portfolioprojects.BikeShop.Production.Entities.Categories;

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
@RequestMapping(path = "api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
  @GetMapping
  public List<categories> getCategories() {
    var CategoriesList = categoryService.getCategory();
    return CategoriesList;
  }

  @GetMapping(path = "id/{CategoriesID}")
  public categories getCategoriesById(@PathVariable("CategoriesID") Long id) {
    var CategoriesList = categoryService.getCategoryById(id);
    return CategoriesList;
  }

  @PostMapping
  public String addCategories(@RequestBody categories Categories) {
    String response = categoryService.addCategory(Categories);
    return response;
  }

  @DeleteMapping(path = "{CategoriesID}")
  public int deleteCategories(@PathVariable("CategoriessId") Long id) {
    return categoryService.deleteCategory(id);
  }

  @PutMapping(path = "{CategoriesID}")
  public int updateCategories(
    @PathVariable("CategoriesID") Long id,
    @RequestBody categories Categories
  ) {
    return categoryService.updateCategoryName(id, Categories);
  }
}
