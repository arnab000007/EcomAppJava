package com.example.ecomappjava.dtoconverter;

import com.example.ecomappjava.dtos.cart.CartDto;
import com.example.ecomappjava.dtos.cart.CartItemDto;
import com.example.ecomappjava.dtos.cart.CheckOutResponseDto;
import com.example.ecomappjava.dtos.cart.ProductBasicInfoDto;
import com.example.ecomappjava.models.Cart;
import com.example.ecomappjava.models.CartItem;
import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDtoConverter {

    public static CartItemDto from(CartItem cartItem){
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(cartItem.getId());
        cartItemDto.setProduct(CartDtoConverter.from(cartItem.getProduct()));
        cartItemDto.setQuantity(cartItem.getQuantity());
        return cartItemDto;
    }

    public static CartDto from(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        List<CartItemDto> items = new ArrayList<>();

        for(CartItem cartItem: cart.getCartItems()){
            items.add(from(cartItem));
        }

        cartDto.setItems(items);
        cartDto.setUser(AuthDtosConveter.from(cart.getUser()));
        return cartDto;
    }
    public static ProductBasicInfoDto from(Product product){
        ProductBasicInfoDto productBasicInfoDto = new ProductBasicInfoDto();
        productBasicInfoDto.setId(product.getId());
        productBasicInfoDto.setName(product.getName());
        productBasicInfoDto.setPrice(product.getPrice());
        productBasicInfoDto.setImageUrl(product.getImageUrl());
        return productBasicInfoDto;
    }

    public static CheckOutResponseDto from (EcomOrder order){
        CheckOutResponseDto checkOutResponseDto = new CheckOutResponseDto();
        checkOutResponseDto.setOrderId(order.getId());
        checkOutResponseDto.setOrderStatus(order.getOrderStatus());
        return checkOutResponseDto;
    }

}
