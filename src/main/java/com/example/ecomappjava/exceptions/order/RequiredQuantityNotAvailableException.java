package com.example.ecomappjava.exceptions.order;

public class RequiredQuantityNotAvailableException extends RuntimeException{
    public RequiredQuantityNotAvailableException(String message) {
        super(message);
    }
}
