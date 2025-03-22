package com.example.ecomappjava.dtoconverter;

import com.example.ecomappjava.dtos.auth.AddressDto;
import com.example.ecomappjava.dtos.auth.UserDto;
import com.example.ecomappjava.models.Address;
import com.example.ecomappjava.models.AuthUser;

public class AuthDtosConveter {
    public static UserDto from(AuthUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());


        return userDto;
    }

    public static AddressDto from(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressLine1(address.getAddressLine1());
        addressDto.setAddressLine2(address.getAddressLine2());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getCountryState());
        addressDto.setCountry(address.getCountry());
        addressDto.setZipCode(address.getZipCode());
        addressDto.setPhoneNumber(address.getPhoneNumber());
        addressDto.setAddressType(address.getAddressType());
        addressDto.setIsDefault(address.getIsDefault());
        return addressDto;
    }

}
