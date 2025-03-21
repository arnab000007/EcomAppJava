package com.example.ecomappjava.services.cart;

import com.example.ecomappjava.Reporsitory.CartItemRepository;
import com.example.ecomappjava.Reporsitory.CartRepository;
import com.example.ecomappjava.exceptions.auth.UserDoesnotExistException;
import com.example.ecomappjava.exceptions.cart.CartNotAvailableException;
import com.example.ecomappjava.exceptions.cart.ProductNotAvailableException;
import com.example.ecomappjava.exceptions.product.ProductNotFoundException;
import com.example.ecomappjava.models.*;
import com.example.ecomappjava.services.auth.IAuthService;
import com.example.ecomappjava.services.order.IOrderService;
import com.example.ecomappjava.services.payment.IPaymentService;
import com.example.ecomappjava.services.product.IProductService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private IProductService productService;
    @Autowired
    private IAuthService authService;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IPaymentService paymentService;

    @Override
    public Cart addToCart(Long productId, Long userId, int quantity) {
        Product product = getProduct(productId);

        AuthUser user = getUser(userId);

        Cart cart = cartRepository.findCartByUser_Id(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setState(State.ACTIVE);
            cartRepository.save(newCart);
            return newCart;
        });

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElse(null);

        if( cartItem != null ) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setState(State.ACTIVE);
            cart.addCartItem(cartItem);
        }
        if( cartItem.getQuantity() > product.getStock() ) {
            throw new ProductNotAvailableException("Available Product quantity is less than requested quantity");
        }

        return  cartRepository.save(cart);
    }

    @Override
    public Cart removeFromCart(Long productId, Long userId, int quantity) {

        Product product = getProduct(productId);

        AuthUser user = getUser(userId);

        Cart cart = cartRepository.findCartByUser_Id(userId)
                .orElseThrow(() ->new CartNotAvailableException("Cart not found"));


        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).findFirst().orElseThrow(() -> new ProductNotAvailableException("Product not found in cart"));


        if( cartItem.getQuantity() < quantity ) {
            throw new ProductNotAvailableException("Requested quantity is greater than added quantity in cart");
        }

        if(quantity == cartItem.getQuantity()) {
            cart.removeCartItem(cartItem);
            cartItemRepository.delete(cartItem);
        }
        else{
            cartItem.setQuantity(cartItem.getQuantity() - quantity);
        }
        if(cart.getCartItems().isEmpty()) {
            cartRepository.delete(cart);
            return null;
        }
        else{
            return cartRepository.save(cart);
        }


    }


    @Override
    public Boolean clearCart(Long userId) {
        Cart cart = cartRepository.findCartByUser_Id(userId)
                .orElseThrow(() ->new CartNotAvailableException("Cart not found"));

        for(CartItem cartItem : cart.getCartItems()) {
            cartItemRepository.delete(cartItem);
        }

        cartRepository.delete(cart);

        return true;
    }

    @Override
    public Pair<EcomOrder, String> checkout(Long userId) {
        AuthUser user = getUser(userId);

        Cart cart = cartRepository.findCartByUser_Id(userId)
                .orElseThrow(() ->new CartNotAvailableException("Cart not found"));

        Pair<EcomOrder, String> orderInfo = orderService.createOrder(cart, user);

        this.clearCart(userId);


        return orderInfo;

    }

    @Override
    public Cart getCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findCartByUser_Id(userId);
        return cartOptional.orElse(null);
    }

    private AuthUser getUser(Long userId) {
        AuthUser user = authService.getUserByid(userId);

        if (user == null) {
            throw new UserDoesnotExistException("User not found");
        }
        return user;
    }

    private Product getProduct(Long productId) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            throw new ProductNotFoundException("Product not found");

        }
        return product;
    }
}
