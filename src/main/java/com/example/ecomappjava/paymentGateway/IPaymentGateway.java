package com.example.ecomappjava.paymentGateway;

import com.example.ecomappjava.models.EcomOrder;

public interface IPaymentGateway {
    String generatePaymentLink(EcomOrder order);

    boolean verifyPaymentLink(String paymentLinkRefId, String paymentId, String paymentLinkStatus, String paymentLinkId, String razorpaySignature);
}
