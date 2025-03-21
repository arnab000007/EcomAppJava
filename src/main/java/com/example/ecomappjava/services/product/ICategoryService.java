package com.example.ecomappjava.services.product;

import com.example.ecomappjava.models.Category;

public interface ICategoryService {
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    Category getCategoryById(Long id);
}
