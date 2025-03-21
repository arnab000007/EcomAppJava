package com.example.ecomappjava.exceptions.auth;

import com.example.ecomappjava.exceptions.BadRequestException;

public class UserAlreadyExistException extends BadRequestException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
