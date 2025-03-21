package com.example.ecomappjava.dtoconverter;

import com.example.ecomappjava.dtos.product.CategoryDto;
import com.example.ecomappjava.dtos.product.ProductDto;
import com.example.ecomappjava.models.Category;
import com.example.ecomappjava.models.Product;

public class ProductDtoConverter {
    public static Product from(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setStock(productDto.getQuantity());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setDescription(productDto.getCategory().getDescription());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
    }

    public static ProductDto from(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setQuantity(product.getStock());
        if (product.getCategory() != null) {

            CategoryDto category = new CategoryDto();
            category.setId(product.getCategory().getId());
            category.setDescription(product.getCategory().getDescription());
            category.setName(product.getCategory().getName());

            productDto.setCategory(category);
        }
        return productDto;
    }

    public static CategoryDto from(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());
        return categoryDto;
    }

    public static Category from(CategoryDto categoryDto){
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }


}
