package com.example.ecomappjava.dtos.cart;

import com.example.ecomappjava.dtos.auth.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartDto {
    private Long id;
    private UserDto user;
    private List<CartItemDto> items = new ArrayList<>();

}
