package com.example.ecomappjava.dtos.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private Long productId;
    private String productName;
    private int quantity;
    private double pricePerQauntity;

}
