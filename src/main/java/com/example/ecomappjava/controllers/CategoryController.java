package com.example.ecomappjava.controllers;

import com.example.ecomappjava.dtoconverter.ProductDtoConverter;
import com.example.ecomappjava.dtos.product.CategoryDto;
import com.example.ecomappjava.exceptions.product.CategoryNotFoundException;
import com.example.ecomappjava.models.Category;
import com.example.ecomappjava.services.product.CategoryService;
import com.example.ecomappjava.utility.auth.anotation.RoleRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    @RoleRequired("ADMIN")
    public CategoryDto addCategory(@RequestBody CategoryDto category){
        return ProductDtoConverter.from(categoryService.addCategory(ProductDtoConverter.from(category)));
    }
    @PutMapping("/{id}")
    @RoleRequired("ADMIN")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody CategoryDto category){
        return ProductDtoConverter.from(categoryService.updateCategory(id, ProductDtoConverter.from(category)));
    }
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id){
        Category cat = categoryService.getCategoryById(id);
        if (cat == null){
            throw new CategoryNotFoundException("Category not found");
        }
        else{
            return ProductDtoConverter.from(cat);
        }
    }


}
