package com.example.ecomappjava.dtos.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemToCartRequestDto {
    private Long productId;
    private int quantity;
}
