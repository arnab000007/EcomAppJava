package com.example.ecomappjava.services.order;

import com.example.ecomappjava.Reporsitory.CartRepository;
import com.example.ecomappjava.Reporsitory.OrderRepository;
import com.example.ecomappjava.exceptions.cart.CartNotAvailableException;
import com.example.ecomappjava.exceptions.order.OrderIsAlreadyCancelledException;
import com.example.ecomappjava.exceptions.order.OrderNotFoundException;
import com.example.ecomappjava.exceptions.order.RequiredQuantityNotAvailableException;
import com.example.ecomappjava.models.*;
import com.example.ecomappjava.services.payment.IPaymentService;
import com.example.ecomappjava.services.product.ProductService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private IPaymentService paymentService;

    @Override
    public Pair<EcomOrder, String> createOrder(Cart cart, AuthUser user, Address address) {

        EcomOrder order = new EcomOrder();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.CREATED);
        Double total = 0.0;
        order.setState(State.ACTIVE);
        order.setAddress(address);

        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getQuantity() > cartItem.getProduct().getStock()){
                throw new RequiredQuantityNotAvailableException("Required Quantity are not in stock");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            orderItem.setState(State.ACTIVE);
            total += orderItem.getPrice() * orderItem.getQuantity();
            productService.updateProductStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }

        OrderStatusTimeMapping orderStatusTimeMapping = new OrderStatusTimeMapping();
        orderStatusTimeMapping.setOrderStatus(OrderStatus.CREATED);
        orderStatusTimeMapping.setOrder(order);
        orderStatusTimeMapping.setState(State.ACTIVE);
        order.getOrderTimeline().add(orderStatusTimeMapping);
        order.setTotalAmount(total);
        order = orderRepository.save(order);

        String paymentLink = paymentService.generatePaymentLink(order);

        return new Pair<>(order, paymentLink);

    }

    @Override
    public EcomOrder getOrderById(Long orderId) {
        return orderRepository.findEcomOrderById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Override
    public List<EcomOrder> getAllOrders(Long userId) {
        return orderRepository.findAllByUser_Id(userId);
    }

    @Override
    public EcomOrder updateOrderStatus(Long orderId, OrderStatus orderStatus) {

        if(orderStatus == OrderStatus.CANCELLED){
            return cancelOrder(orderId);
        }

        EcomOrder order = getOrderById(orderId);

        if(order.getOrderStatus() == OrderStatus.CANCELLED){
            throw new OrderIsAlreadyCancelledException("Order is already cancelled");
        }

        order.setOrderStatus(orderStatus);

        OrderStatusTimeMapping orderStatusTimeMapping = new OrderStatusTimeMapping();
        orderStatusTimeMapping.setOrderStatus(orderStatus);
        orderStatusTimeMapping.setOrder(order);
        orderStatusTimeMapping.setState(State.ACTIVE);
        order.getOrderTimeline().add(orderStatusTimeMapping);


        order = orderRepository.save(order);

        return order;
    }

    @Override
    public EcomOrder cancelOrder(Long orderId) {
        EcomOrder order = getOrderById(orderId);
        order.setOrderStatus(OrderStatus.CANCELLED);

        for(OrderItem orderItem : order.getOrderItems()){
            productService.updateProductStock(orderItem.getProduct().getId(), -orderItem.getQuantity());
        }

        OrderStatusTimeMapping orderStatusTimeMapping = new OrderStatusTimeMapping();
        orderStatusTimeMapping.setOrderStatus(OrderStatus.CANCELLED);
        orderStatusTimeMapping.setOrder(order);
        orderStatusTimeMapping.setState(State.ACTIVE);
        order.getOrderTimeline().add(orderStatusTimeMapping);

        order = orderRepository.save(order);
        return order;
    }

    public boolean UpdatePaymentStatus(String paymentLinkRefId, String paymentId, String paymentLinkId, boolean status){
        EcomOrder order = this.getOrderById(Long.parseLong(paymentLinkRefId));
        Payment payment = new Payment();
        payment.setPaymentMethod(paymentId);
        payment.setOrder(order);
        payment.setTransactionId(paymentLinkId);
        payment.setState(State.ACTIVE);

        if(status){
            payment.setStatus(PaymentStatus.COMPLETED);
            this.updateOrderStatus(order.getId(), OrderStatus.PROCESSING);
        }else {
            payment.setStatus(PaymentStatus.FAILED);
            this.updateOrderStatus(order.getId(), OrderStatus.PAYMENT_PENDING);
        }
        paymentService.savePayment(payment);
        return status;
    }
}
