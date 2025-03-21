package com.example.ecomappjava.controllers;

import com.example.ecomappjava.dtoconverter.ProductDtoConverter;
import com.example.ecomappjava.dtos.product.CategoryDto;
import com.example.ecomappjava.dtos.product.ProductDto;
import com.example.ecomappjava.dtos.product.SearchDto;
import com.example.ecomappjava.exceptions.product.CategoryNotFoundException;
import com.example.ecomappjava.exceptions.product.ProductNotFoundException;
import com.example.ecomappjava.models.Category;
import com.example.ecomappjava.models.Product;
import com.example.ecomappjava.models.Role;
import com.example.ecomappjava.services.product.IProductService;
import com.example.ecomappjava.utility.auth.JwtUtil;
import com.example.ecomappjava.utility.auth.anotation.RoleRequired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/add")
    @RoleRequired("ADMIN")
    public ProductDto addProduct(@RequestBody ProductDto productDto) {


        Product product = productService.addProduct(ProductDtoConverter.from(productDto));
        return ProductDtoConverter.from(product);
    }

    @PostMapping("/search")
    public Page<ProductDto> searchProducts(@RequestBody SearchDto searchDto){

        List<ProductDto> results = new ArrayList<>();


        Page<Product> products = productService.searchProducts(searchDto.getQuery(), searchDto.getPageNumber(), searchDto.getPageSize(), searchDto.getSortParams());
        for(Product product : products){
            results.add(ProductDtoConverter.from(product));
        }

        Page<ProductDto> page = new PageImpl<>(results, products.getPageable(), products.getTotalElements());
        return  page;
    }
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id")  Long id) {
        Product product = productService.getProductById(id);
        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }
        else {
            return ProductDtoConverter.from(product);
        }
    }

    @PutMapping("/{id}")
    @RoleRequired("ADMIN")
    public ProductDto updateProduct(@PathVariable("id")  Long id, @RequestBody ProductDto productDto) {
        Product product = productService.updateProduct(id, ProductDtoConverter.from(productDto));
        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }
        else {
            return ProductDtoConverter.from(product);
        }
    }

    @GetMapping("category/{name}")
    public List<ProductDto> getProductByCategory(@PathVariable("name") String name){
        List<Product> products = productService.getAllProductsForCategory(name);
        List<ProductDto> results = new ArrayList<>();

        for(Product product : products){
            results.add(ProductDtoConverter.from(product));
        }

        return results;
    }
}
