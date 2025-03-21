package com.example.ecomappjava.dtoconverter;

import com.example.ecomappjava.dtos.auth.UserDto;
import com.example.ecomappjava.models.AuthUser;

public class AuthDtosConveter {
    public static UserDto from(AuthUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());


        return userDto;
    }

}
