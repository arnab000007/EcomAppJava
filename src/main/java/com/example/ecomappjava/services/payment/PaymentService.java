package com.example.ecomappjava.services.payment;

import com.example.ecomappjava.Reporsitory.PaymentRepository;
import com.example.ecomappjava.models.*;
import com.example.ecomappjava.paymentGateway.IPaymentGateway;
import com.example.ecomappjava.services.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentGateway paymentGateway;

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public String generatePaymentLink(EcomOrder order) {
        return paymentGateway.generatePaymentLink(order);
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    public boolean verifyPayment(String paymentLinkRefId, String paymentId, String paymentLinkStatus, String paymentLinkId, String razorpaySignature) {
        try {
           boolean status = paymentGateway.verifyPaymentLink(paymentLinkRefId, paymentId, paymentLinkStatus, paymentLinkId, razorpaySignature);


           return status;
        } catch (Exception e) {
            return false;
        }
    }

}
