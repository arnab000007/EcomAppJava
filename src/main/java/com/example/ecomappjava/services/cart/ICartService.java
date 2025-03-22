package com.example.ecomappjava.services.cart;

import com.example.ecomappjava.models.Cart;
import com.example.ecomappjava.models.EcomOrder;
import org.antlr.v4.runtime.misc.Pair;

public interface ICartService {
    Cart addToCart(Long productId, Long userId, int quantity);
    Cart removeFromCart(Long productId, Long userId, int quantity);
    Boolean clearCart(Long userId);
    Pair<EcomOrder, String> checkout(Long userId, Long addressId);
    Cart getCart(Long userId);
}
