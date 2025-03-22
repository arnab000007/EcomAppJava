package com.example.ecomappjava.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class AuthUser extends BaseModel{
    private String name;
    private String email;
    private String password;
    private String username;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EcomOrder> orders;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @OneToMany(mappedBy = "User", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
}
