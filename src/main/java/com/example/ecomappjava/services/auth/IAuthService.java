package com.example.ecomappjava.services.auth;

import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.Role;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface IAuthService {
    AuthUser signup(String email, String password, String name, String username, List<Role> roles);

    Pair<AuthUser, MultiValueMap<String,String>> login(String email, String password);

    AuthUser getUserByid(Long id);
}
