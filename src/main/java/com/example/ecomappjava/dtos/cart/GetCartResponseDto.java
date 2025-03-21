package com.example.ecomappjava.dtos.cart;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCartResponseDto {
    private String message;
    private CartDto cart;
}
