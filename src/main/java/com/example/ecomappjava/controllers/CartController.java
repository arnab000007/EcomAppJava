package com.example.ecomappjava.controllers;

import com.example.ecomappjava.dtoconverter.CartDtoConverter;
import com.example.ecomappjava.dtos.cart.AddItemToCartRequestDto;
import com.example.ecomappjava.dtos.cart.CheckOutResponseDto;
import com.example.ecomappjava.dtos.cart.GetCartResponseDto;
import com.example.ecomappjava.exceptions.auth.UserDoesnotExistException;
import com.example.ecomappjava.exceptions.cart.CartNotAvailableException;
import com.example.ecomappjava.exceptions.cart.ProductNotAvailableException;
import com.example.ecomappjava.exceptions.order.RequiredQuantityNotAvailableException;
import com.example.ecomappjava.exceptions.product.ProductNotFoundException;
import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.Cart;
import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.services.cart.CartService;
import com.example.ecomappjava.utility.auth.anotation.RoleRequired;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.antlr.v4.runtime.misc.Pair;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping()
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public GetCartResponseDto getCartInformation(HttpServletRequest request){
        AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserDoesnotExistException("User not found in request");
        }

        Cart cart = cartService.getCart(currentUser.getId());
        GetCartResponseDto responseDto = new GetCartResponseDto();
        if (cart == null) {
            responseDto.setMessage("Cart not found");
        }
        else{
            responseDto.setCart(CartDtoConverter.from(cart));
        }

        return responseDto;
    }

    @PostMapping("/additems")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public GetCartResponseDto addItemToCart(@RequestBody AddItemToCartRequestDto requestBody, HttpServletRequest request){
        AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserDoesnotExistException("User not found in request");
        }
        GetCartResponseDto responseDto = new GetCartResponseDto();
        try {
            Cart cart = cartService.addToCart(requestBody.getProductId(), currentUser.getId(), requestBody.getQuantity());

            if (cart == null) {
                responseDto.setMessage("Cart not found");
            } else {
                responseDto.setCart(CartDtoConverter.from(cart));
            }
        }
        catch (UserDoesnotExistException e) {
            responseDto.setMessage("User not found");
        }
        catch (ProductNotFoundException e){
            responseDto.setMessage("Product not found");
        }
        catch (ProductNotAvailableException e){
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @PostMapping("/removeitems")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public GetCartResponseDto removeItemToCart(@RequestBody AddItemToCartRequestDto requestBody, HttpServletRequest request){
        AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserDoesnotExistException("User not found in request");
        }
        GetCartResponseDto responseDto = new GetCartResponseDto();
        try {
            Cart cart = cartService.removeFromCart(requestBody.getProductId(), currentUser.getId(), requestBody.getQuantity());

            if (cart == null) {
                responseDto.setMessage("Cart is empty");
            } else {
                responseDto.setCart(CartDtoConverter.from(cart));
            }
        }
        catch (UserDoesnotExistException | ProductNotFoundException | ProductNotAvailableException |
               CartNotAvailableException e) {
            responseDto.setMessage(e.getMessage());
        }

        return responseDto;
    }

    @PostMapping("/clearcart")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public boolean clearCart(HttpServletRequest request){
        try {
            AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
            if (currentUser == null) {
                throw new UserDoesnotExistException("User not found in request");
            }

            return cartService.clearCart(currentUser.getId());
        }
        catch (CartNotAvailableException e) {
            return false;
        }
    }


    @PostMapping("/checkout")
    @RoleRequired(value = "NORMAL_USER", checkUser = true)
    public CheckOutResponseDto checkout(HttpServletRequest request){
        try {
            AuthUser currentUser = (AuthUser) request.getAttribute("currentUser");
            if (currentUser == null) {
                throw new UserDoesnotExistException("User not found in request");
            }

            Pair<EcomOrder, String> orderInfo = cartService.checkout(currentUser.getId());
            CheckOutResponseDto response = CartDtoConverter.from(orderInfo.a);
            response.setPaymentLink(orderInfo.b);
            return response;
        }
        catch (CartNotAvailableException | UserDoesnotExistException | RequiredQuantityNotAvailableException e) {
            CheckOutResponseDto response = new CheckOutResponseDto();
            response.setMessage(e.getMessage());
            return response;
        }
    }

}
