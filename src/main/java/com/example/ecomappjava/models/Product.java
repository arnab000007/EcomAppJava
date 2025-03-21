package com.example.ecomappjava.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseModel{

    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private Boolean isPrime;
    private int stock;


    @ManyToOne
    @Cascade(CascadeType.ALL)
    @JsonManagedReference
    private Category category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductRating> ratings;
}
