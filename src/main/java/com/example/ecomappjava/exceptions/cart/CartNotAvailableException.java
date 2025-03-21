package com.example.ecomappjava.exceptions.cart;

public class CartNotAvailableException extends RuntimeException{
    public CartNotAvailableException(String message) {
        super(message);
    }
}
