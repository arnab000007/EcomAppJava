package com.example.ecomappjava.dtos.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    private  Long id;
    private String name;
    private String description;
}