package com.example.ecomappjava.dtos.cart;

import com.example.ecomappjava.dtos.product.ProductDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemDto {
    private Long id;
    private ProductBasicInfoDto product;
    private int quantity;
}
