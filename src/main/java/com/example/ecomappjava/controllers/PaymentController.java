package com.example.ecomappjava.controllers;

import com.example.ecomappjava.models.OrderStatus;
import com.example.ecomappjava.services.order.IOrderService;
import com.example.ecomappjava.services.payment.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/razorpay/callback")
    public String razorpayCallback(@RequestParam("razorpay_payment_id") String paymentId,
                                 @RequestParam("razorpay_payment_link_id") String linkId,
                                 @RequestParam("razorpay_payment_link_reference_id") String orderId,
                                @RequestParam("razorpay_payment_link_status") String status,
                                 @RequestParam("razorpay_signature") String singature) {
        // handle razorpay callback

        boolean verifyStatus = paymentService.verifyPayment(orderId, paymentId, status, linkId, singature);
        if (verifyStatus) {
            orderService.updateOrderStatus(Long.parseLong(orderId), OrderStatus.PROCESSING);
            return "Payment successful";
        } else {
            return "Payment failed";
        }
    }
}
