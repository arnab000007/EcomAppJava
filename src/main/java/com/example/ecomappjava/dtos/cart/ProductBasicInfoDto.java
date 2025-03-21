package com.example.ecomappjava.dtos.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductBasicInfoDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
}
