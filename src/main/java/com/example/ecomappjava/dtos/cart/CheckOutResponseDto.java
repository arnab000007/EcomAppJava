package com.example.ecomappjava.dtos.cart;

import com.example.ecomappjava.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckOutResponseDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private String message;
    private String paymentLink;
}
