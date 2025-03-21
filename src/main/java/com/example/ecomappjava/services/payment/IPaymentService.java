package com.example.ecomappjava.services.payment;

import com.example.ecomappjava.models.EcomOrder;
import com.example.ecomappjava.models.Payment;

public interface IPaymentService {
    String generatePaymentLink(EcomOrder order);
    Payment savePayment(Payment payment);
    boolean verifyPayment(String paymentLinkRefId, String paymentId, String paymentLinkStatus, String paymentLinkId, String razorpaySignature);
}
