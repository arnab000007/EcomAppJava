package com.example.ecomappjava.paymentGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentGatewayStrategy {

    @Autowired
    private RazorPayPaymentGateway razorpayPaymentGateway;

    public IPaymentGateway getBestPaymentGateway() {
        return razorpayPaymentGateway;
    }

}
