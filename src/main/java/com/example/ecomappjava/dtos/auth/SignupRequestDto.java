package com.example.ecomappjava.dtos.auth;

import com.example.ecomappjava.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;
    private String username;

    private List<Role> roles = new ArrayList<>();

}
