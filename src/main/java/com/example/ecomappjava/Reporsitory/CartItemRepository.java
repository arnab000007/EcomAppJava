package com.example.ecomappjava.Reporsitory;

import com.example.ecomappjava.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    void delete(CartItem entity);

    CartItem save(CartItem entity);
}
