package com.portfolioprojects.BikeShop.Production.Entities.Categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CategoryService {
    @Autowired
    private CategoryRepository CategoryRepository;

    public List<categories> getCategory() {
        var Categories = CategoryRepository.findAll();
        return Categories;
    }

    public categories getCategoryById(Long id) {

        return CategoryRepository.findById(id).get();
    }

    public String addCategory(categories Categories) {
        var found = CategoryRepository.findCategoryByName(Categories.getCategory_name());
        if(found.isPresent())
        {
            throw new IllegalStateException("Category Name Already Exists in DB");
        }
        else
        {
            CategoryRepository.save(Categories);
        }
        return "Category " + Categories.getCategory_id() + " added";
    }

    public int deleteCategory(Long id) {
        var found = CategoryRepository.findById(id);
        if(found.isPresent())
        {
            CategoryRepository.deleteById(id);
        }
        else
        {
     
            throw new IllegalStateException("Category with id " + id + " not found. Check ID");
        }
        return 1;
    }

    public int updateCategoryName(Long id, categories Categories) {
        var found = CategoryRepository.findById(id);
        if(found.isPresent())
        {
            CategoryRepository.UpdateCategoryName(Categories.getCategory_name(), id);
        }
        else
        {
     
            throw new IllegalStateException("Category with id " + id + " not found. Check ID");
        }
        return 1;

    }
}
