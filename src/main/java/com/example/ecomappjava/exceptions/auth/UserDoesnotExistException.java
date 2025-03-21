package com.example.ecomappjava.exceptions.auth;

import com.example.ecomappjava.exceptions.BadRequestException;

public class UserDoesnotExistException extends BadRequestException {
    public UserDoesnotExistException(String message) {
        super(message);
    }
}