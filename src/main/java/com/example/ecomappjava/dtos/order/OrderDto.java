package com.example.ecomappjava.dtos.order;

import com.example.ecomappjava.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long orderId;
    private OrderStatus orderStatus;
    private String orderDate;
    private Double orderAmount;
    private List<OrderItemDto> orderItems = new ArrayList<>();
    private List<OrderTimelineDto> orderTimeline = new ArrayList<>();
}
