package com.example.ecomappjava.exceptions.product;

import com.example.ecomappjava.exceptions.UnauthorizedException;

public class UnauthorizedCreateProduct extends UnauthorizedException {
    public UnauthorizedCreateProduct(String message) {
        super(message);
    }
}
