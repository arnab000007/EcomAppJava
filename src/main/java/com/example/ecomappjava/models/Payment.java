package com.example.ecomappjava.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment extends BaseModel{
    @ManyToOne
    @JoinColumn(name = "order_id")
    private EcomOrder order;

    private String paymentMethod;
    private String transactionId;

    private PaymentStatus status;
}
