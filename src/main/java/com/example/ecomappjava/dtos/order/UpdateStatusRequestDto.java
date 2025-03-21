package com.example.ecomappjava.dtos.order;

import com.example.ecomappjava.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequestDto {
    private Long orderId;
    private OrderStatus status;
}
