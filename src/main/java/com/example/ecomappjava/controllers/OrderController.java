package com.example.ecomappjava.controllers;

import com.example.ecomappjava.dtoconverter.OrderDtoConverter;
import com.example.ecomappjava.dtos.order.OrderDto;
import com.example.ecomappjava.dtos.order.UpdateStatusRequestDto;
import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.services.order.IOrderService;
import com.example.ecomappjava.utility.auth.anotation.RoleRequired;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/{id}")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public OrderDto getOrder(@PathVariable Long id, HttpServletRequest request) {
        return OrderDtoConverter.from(orderService.getOrderById(id));
    }

    @GetMapping("")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public List<OrderDto> getAllOrder(HttpServletRequest request) {
        AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
        List<EcomOrder> orderList = orderService.getAllOrders(currentUser.getId());
        List<OrderDto> response = new ArrayList<>();
        for(EcomOrder order : orderList) {
            OrderDtoConverter.from(order);
        }

        return response;
    }

    @PostMapping("/cancel/{id}")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public OrderDto cancelOrder(@PathVariable Long id, HttpServletRequest request) {
        return OrderDtoConverter.from(orderService.cancelOrder(id));
    }

    @PostMapping("/update/{id}")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public OrderDto updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequestDto requestDto, HttpServletRequest request) {
        return OrderDtoConverter.from(orderService.updateOrderStatus(id, requestDto.getStatus()));
    }
}
