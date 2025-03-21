package com.example.ecomappjava.paymentGateway;

import com.example.ecomappjava.models.EcomOrder;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RazorPayPaymentGateway implements IPaymentGateway{

    @Autowired
    private RazorpayClient razorpayClient;

    @Value("${razorpay.keySecret}")
    private String secret;

    @Override
    public String generatePaymentLink(EcomOrder order) {
        try {
            Double totalAmount = order.getTotalAmount() * 100;
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", (long)(order.getTotalAmount() * 100));
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("accept_partial", false);
            paymentLinkRequest.put("expire_by", (System.currentTimeMillis() + (20 * 60 * 1000))/1000);
            paymentLinkRequest.put("reference_id", order.getId().toString());
            paymentLinkRequest.put("description", "Order Placed");
            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getName());
            customer.put("contact", "+910000000000");
            customer.put("email", order.getUser().getEmail());
            paymentLinkRequest.put("customer", customer);
            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("reminder_enable", true);
            JSONObject notes = new JSONObject();
            notes.put("note_key", "Payment Link for Order placed.");
            paymentLinkRequest.put("notes", notes);
            paymentLinkRequest.put("callback_url", "http://localhost:8080/payment/razorpay/callback");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
            return payment.get("short_url").toString();
        }
        catch (RazorpayException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean verifyPaymentLink(String paymentLinkRefId, String paymentId, String paymentLinkStatus, String paymentLinkId, String razorpaySignature) {
        try {
            JSONObject options = new JSONObject();
            options.put("payment_link_reference_id", paymentLinkRefId);
            options.put("razorpay_payment_id", paymentId);
            options.put("payment_link_status", paymentLinkStatus);
            options.put("payment_link_id", paymentLinkId);
            options.put("razorpay_signature",razorpaySignature);

            return Utils.verifyPaymentLink(options, secret);
        }
        catch (RazorpayException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
