package com.example.ecomappjava.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EcomOrder extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser user;

    private Double totalAmount;


    private OrderStatus orderStatus;

    @OneToOne
    private Address address;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems =  new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payment =  new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderStatusTimeMapping> orderTimeline = new ArrayList<>();
}
