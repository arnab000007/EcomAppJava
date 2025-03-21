package com.example.ecomappjava.controllers;

import com.example.ecomappjava.exceptions.BadRequestException;
import com.example.ecomappjava.exceptions.UnauthorizedException;
import com.example.ecomappjava.exceptions.auth.IncorrectPasswordException;
import com.example.ecomappjava.exceptions.auth.UserAlreadyExistException;
import com.example.ecomappjava.exceptions.auth.UserDoesnotExistException;
import com.example.ecomappjava.exceptions.product.ProductNotFoundException;
import com.example.ecomappjava.exceptions.product.UnauthorizedCreateProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler({IllegalArgumentException.class, BadRequestException.class, ProductNotFoundException.class})
    public ResponseEntity<String> haldleException(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnauthorizedException.class, IncorrectPasswordException.class, UndeclaredThrowableException.class})
    public ResponseEntity<String> haldleExceptionUnauthorized(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> haldleExceptionOther(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
