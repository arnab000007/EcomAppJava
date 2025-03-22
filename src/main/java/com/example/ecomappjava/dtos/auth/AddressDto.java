package com.example.ecomappjava.dtos.auth;

import com.example.ecomappjava.models.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String phoneNumber;
    private AddressType addressType;
    private Boolean isDefault;
}
