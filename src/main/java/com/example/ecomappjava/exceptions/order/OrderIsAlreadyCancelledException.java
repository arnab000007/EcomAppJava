package com.example.ecomappjava.exceptions.order;

public class OrderIsAlreadyCancelledException extends RuntimeException{
    public OrderIsAlreadyCancelledException(String message) {
        super(message);
    }
}
