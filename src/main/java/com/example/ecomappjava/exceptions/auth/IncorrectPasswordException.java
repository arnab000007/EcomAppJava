package com.example.ecomappjava.exceptions.auth;

import com.example.ecomappjava.exceptions.UnauthorizedException;

public class IncorrectPasswordException extends UnauthorizedException {
    public IncorrectPasswordException(String message) {
        super(message);
    }
}