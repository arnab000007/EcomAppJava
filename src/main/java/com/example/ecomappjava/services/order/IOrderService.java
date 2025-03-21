package com.example.ecomappjava.services.order;

import com.example.ecomappjava.models.AuthUser;
import com.example.ecomappjava.models.Cart;
import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.models.OrderStatus;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public interface IOrderService {
    Pair<EcomOrder, String> createOrder(Cart cart, AuthUser user);
    EcomOrder getOrderById(Long orderId);
    List<EcomOrder> getAllOrders(Long userId);
    EcomOrder updateOrderStatus(Long orderId, OrderStatus orderStatus);
    EcomOrder cancelOrder(Long orderId);
    boolean UpdatePaymentStatus(String paymentLinkRefId, String paymentId, String paymentLinkId, boolean status);
}
