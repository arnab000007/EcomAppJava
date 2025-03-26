package com.example.ecomappjava.services.auth;

import com.example.ecomappjava.Reporsitory.AddressRepository;
import com.example.ecomappjava.Reporsitory.UserReporsitory;
import com.example.ecomappjava.Reporsitory.UserSessionsRepository;
import com.example.ecomappjava.dtos.auth.AddressDto;
import com.example.ecomappjava.exceptions.auth.IncorrectPasswordException;
import com.example.ecomappjava.exceptions.auth.UserAlreadyExistException;
import com.example.ecomappjava.exceptions.auth.UserDoesnotExistException;
import com.example.ecomappjava.exceptions.auth.AddressNotFoundException;
import com.example.ecomappjava.models.*;
import com.example.ecomappjava.utility.auth.JwtUtil;
import org.antlr.v4.runtime.misc.Pair;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService{
    @Autowired
    private UserReporsitory userReporsitory;

    @Autowired
    private UserSessionsRepository userSessionsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public AuthUser signup(String email, String password, String name, String username, List<Role> roles) {
        Optional<AuthUser> optionalAuthUser = userReporsitory.findByEmailAndState(email, State.ACTIVE);
        if (optionalAuthUser.isPresent()) {
            throw new UserAlreadyExistException("User with this email already exists");
        }

        AuthUser newUser = new AuthUser();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setState(State.ACTIVE);
        if (roles == null || roles.isEmpty()) {
            newUser.setRoles(List.of(Role.NORMAL_USER));
        }
        else {
            newUser.setRoles(roles);
        }

        newUser = userReporsitory.save(newUser);

        return newUser;
    }

    @Override
    public Pair<AuthUser, MultiValueMap<String, String>> login(String email, String password) {
        Optional<AuthUser> optionalAuthUser = userReporsitory.findByEmailAndState(email, State.ACTIVE);

        if (optionalAuthUser.isEmpty()){
            throw new UserDoesnotExistException("User with this email does not exist");
        }

        if(!bCryptPasswordEncoder.matches(password, optionalAuthUser.get().getPassword())){
            try {
                throw new IncorrectPasswordException("Incorrect password");
            } catch (IncorrectPasswordException e) {
                throw new RuntimeException(e);
            }
        }

        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("userId", optionalAuthUser.get().getId());
        userClaims.put("roles", optionalAuthUser.get().getRoles().stream().map(Enum::name).collect(Collectors.toList()));
        long currentTime = System.currentTimeMillis();
        userClaims.put("iat", currentTime);
        userClaims.put("exp", currentTime + 1000 * 60 * 60 * 24);
        userClaims.put("issuer","abc.com");

        String token = jwtUtil.generateToken(userClaims);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE, token);

        UserSession userSessions = new UserSession();
        userSessions.setToken(token);
        userSessions.setUser(optionalAuthUser.get());
        userSessions.setState(State.ACTIVE);

        userSessionsRepository.save(userSessions);

        Pair<AuthUser, MultiValueMap<String, String>> response = new Pair<>(optionalAuthUser.get(), headers);
        return response;
    }

    @Override
    public AuthUser getUserByid(Long id) {
        AuthUser user = userReporsitory.findByIdAndState(id, State.ACTIVE).orElse(null);
        return user;
    }

    @Override
    public AuthUser getUserByidWithAddress(Long id) {
        AuthUser user = userReporsitory.findByIdAndState(id, State.ACTIVE).orElse(null);
        if (user != null) {
            Hibernate.initialize(user.getAddresses());
        }

        return user;
    }

    @Override
    public Address addAddress(Long userid, AddressDto addAddressRequestDto) {
        AuthUser user = getUserByidWithAddress(userid);

        if (user == null) {
            throw new UserDoesnotExistException("User not found");
        }

        Address address = new Address();
        UpdateAddressParameter(addAddressRequestDto, address);
        address.setUser(user);
        address.setState(State.ACTIVE);

        address = addressRepository.save(address);

        return address;

    }

    @Override
    public Address updateAddress(Long userid, Long addressId, AddressDto addAddressRequestDto) {
        AuthUser user = getUserByidWithAddress(userid);

        if (user == null) {
            throw new UserDoesnotExistException("User not found");
        }

        Address address = user.getAddresses().stream().filter(a -> {
            return a.getId().equals(addressId);
        }).findFirst().orElse(null);

        if (address == null) {
            throw new AddressNotFoundException("Address not found");
        }

        UpdateAddressParameter(addAddressRequestDto, address);
        address.setState(State.ACTIVE);
        address = addressRepository.save(address);

        return address;
    }

    private void UpdateAddressParameter(AddressDto addAddressRequestDto, Address address) {
        address.setCity(addAddressRequestDto.getCity());
        address.setCountry(addAddressRequestDto.getCountry());
        address.setZipCode(addAddressRequestDto.getZipCode());
        address.setCountryState(addAddressRequestDto.getState());
        address.setAddressLine2(addAddressRequestDto.getAddressLine2());
        address.setAddressLine1(addAddressRequestDto.getAddressLine1());
        address.setAddressType(addAddressRequestDto.getAddressType());
        address.setIsDefault(addAddressRequestDto.getIsDefault());
        if(address.getIsDefault()){
            address.setIsDefault(true);
        }
        address.setPhoneNumber(addAddressRequestDto.getPhoneNumber());
    }
}
