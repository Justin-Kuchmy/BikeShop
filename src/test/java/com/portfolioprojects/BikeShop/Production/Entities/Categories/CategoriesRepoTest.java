package com.portfolioprojects.BikeShop.Production.Entities.Categories;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoriesRepoTest {
    @Autowired
    private CategoryRepository CategoryRepository;
    categories Category = new categories(
            "category_name"
        );
    @Test
	void CategoryRepoTestMethod() {
	}

    @Test
    void ShouldReturnAllCategories()
    {
        //given
        var CategoryList = CategoryRepository.findAll();
        //then
        var size = CategoryRepository.count();
        //when
        assertEquals(CategoryList.size(), size);
    }
    @Test
    void ShouldFindCategoryByEmail()
    {
        //given
        Long firstCategoryID = 1L;
        var CategoryToFindByEmail = CategoryRepository.findById(firstCategoryID).get().getCategory_name();
        var result = CategoryRepository.findCategoryByName(CategoryToFindByEmail);
        //then
        assertNotNull(result.get());
        //when
    }
    @Test
    void ShouldAddNewCategory()
    {
        //given
        //then
        CategoryRepository.save(Category);
        var CategoryInDB = CategoryRepository.findCategoryByName(Category.getCategory_name());
        //when
        assertEquals(Category.getCategory_id(), CategoryInDB.get().getCategory_id());

    }
  
    @Test
    void ShouldUpdateCategoryName()
    {
        //given
        var StartingCategory = CategoryRepository.findCategoryByName(Category.getCategory_name());
        //then
        var EmailToUpdate = "newEmail";
        CategoryRepository.findCategoryByName(EmailToUpdate);
        //when
        assertNotEquals(StartingCategory.get().getCategory_name(), EmailToUpdate);
    }
    @Test
    void ShouldDeleteById()
    {
        //given
        Long CategoryIdToDelete = 1446L;
        CategoryRepository.save(Category); //this Category will be given id 1446 automatically. 
        var StartingCategory = CategoryRepository.findById(CategoryIdToDelete);

        //then
        CategoryRepository.delete(StartingCategory.get());

        //Optional<Categories> will be "Empty" if the Category wasnt found
        var result = CategoryRepository.findById(Long.valueOf(StartingCategory.get().getCategory_id()));
        
        //when
        assertTrue(result.isEmpty());
    }
    

    

   

    

    
}
