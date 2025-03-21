package com.example.ecomappjava.dtos.order;

import com.example.ecomappjava.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderTimelineDto {
    private Long id;
    private OrderStatus orderStatus;
    private String StatusChangeTime;
}
