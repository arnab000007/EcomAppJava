package com.example.ecomappjava.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class UserSession extends BaseModel{
    private String token;

    @ManyToOne
    private AuthUser user;


    private Date expriedAt;
}
