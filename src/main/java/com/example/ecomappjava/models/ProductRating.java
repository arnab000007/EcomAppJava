package com.example.ecomappjava.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductRating extends BaseModel{
    @ManyToOne
    private Product product;
    @ManyToOne
    private AuthUser user;
    private int rating;
    @Column(nullable = true)
    private String review;
}
