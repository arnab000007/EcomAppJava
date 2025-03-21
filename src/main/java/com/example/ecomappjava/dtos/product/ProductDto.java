package com.example.ecomappjava.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int quantity;
    private CategoryDto category;
}
