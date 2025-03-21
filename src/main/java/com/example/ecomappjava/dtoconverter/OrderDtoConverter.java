package com.example.ecomappjava.dtoconverter;

import com.example.ecomappjava.dtos.order.OrderDto;
import com.example.ecomappjava.dtos.order.OrderItemDto;
import com.example.ecomappjava.dtos.order.OrderTimelineDto;
import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.models.OrderItem;
import com.example.ecomappjava.models.OrderStatusTimeMapping;
import com.razorpay.Order;

public class OrderDtoConverter {
    public static OrderDto from(EcomOrder order) {
        OrderDto orderDto = new OrderDto();

        orderDto.setOrderId(order.getId());
        orderDto.setOrderDate(order.getCreatedAt().toString());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setOrderAmount(order.getTotalAmount());

        for(OrderItem item : order.getOrderItems()) {
            OrderItemDto orderItemDto = from(item);
            orderDto.getOrderItems().add(orderItemDto);
        }

        for(OrderStatusTimeMapping orderStatusTimeMapping : order.getOrderTimeline()) {
            OrderTimelineDto orderTimelineDto = from(orderStatusTimeMapping);
            orderDto.getOrderTimeline().add(orderTimelineDto);
        }


        return orderDto;
    }

    public static OrderItemDto from(OrderItem item) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(item.getProduct().getId());
        orderItemDto.setProductName(item.getProduct().getName());
        orderItemDto.setPricePerQauntity(item.getPrice());
        orderItemDto.setQuantity(item.getQuantity());

        return orderItemDto;
    }

    public static OrderTimelineDto from(OrderStatusTimeMapping orderStatusTimeMapping) {
        OrderTimelineDto orderTimelineDto = new OrderTimelineDto();
        orderTimelineDto.setId(orderStatusTimeMapping.getId());
        orderTimelineDto.setStatusChangeTime(orderStatusTimeMapping.getCreatedAt().toString());
        orderTimelineDto.setOrderStatus(orderStatusTimeMapping.getOrderStatus());

        return orderTimelineDto;
    }
}
