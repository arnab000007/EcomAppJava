package com.example.ecomappjava.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseModel{
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String countryState;
    private String country;
    private String zipCode;
    private String phoneNumber;
    private AddressType addressType;
    private Boolean isDefault;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AuthUser User;
}
