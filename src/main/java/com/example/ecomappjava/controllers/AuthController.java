package com.example.ecomappjava.controllers;

import com.example.ecomappjava.dtoconverter.AuthDtosConveter;
import com.example.ecomappjava.dtos.auth.LoginRequestDto;
import com.example.ecomappjava.dtos.auth.SignupRequestDto;
import com.example.ecomappjava.dtos.auth.UserDto;
import com.example.ecomappjava.exceptions.auth.IncorrectPasswordException;
import com.example.ecomappjava.exceptions.auth.UserAlreadyExistException;
import com.example.ecomappjava.exceptions.auth.UserDoesnotExistException;
import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.services.auth.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignupRequestDto signupRequestDto) {
        try {
            AuthUser user = authService.signup(signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getName(), signupRequestDto.getUsername(), signupRequestDto.getRoles());
            return AuthDtosConveter.from(user);
        }catch (UserAlreadyExistException exception) {
            throw exception;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {

            Pair<AuthUser, MultiValueMap<String,String>> userInfo = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            UserDto user = AuthDtosConveter.from(userInfo.a);
            return new ResponseEntity<>(user, userInfo.b, org.springframework.http.HttpStatus.OK);


    }

}
