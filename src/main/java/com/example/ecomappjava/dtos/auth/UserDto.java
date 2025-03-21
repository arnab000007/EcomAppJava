package com.example.ecomappjava.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private String username;
}
