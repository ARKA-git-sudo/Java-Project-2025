package com.example.Payment;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    EmailService emailService;

    @Autowired
    PaymentRepo paymentRepo;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @Value("${razorpay.key_id}")
    private String keyId;



    public String createOrder(MyPayment orderDetails) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",(int)((orderDetails.getAmount())*100));
        orderRequest.put("currency","INR");
        orderRequest.put("receipt","txn_"+ UUID.randomUUID());
        Order razorpayorder = client.orders.create(orderRequest);
        orderDetails.setOrderId(razorpayorder.get("id"));
        orderDetails.setStatus("CREATED");
        orderDetails.setCreatedAt(LocalDateTime.now());

        paymentRepo.save(orderDetails);
        return razorpayorder.toString();


    }

    public void updateOrderStatus(String paymentId, String orderId, String status) {
        MyPayment order = paymentRepo.findByOrderId(orderId);
        order.setPaymentId(paymentId);
        order.setStatus(status);
        paymentRepo.save(order);

        if("SUCCESS".equalsIgnoreCase(status)){
            emailService.sendEmail(order.getEmail(), order.getName(), order.getCourseName(), order.getAmount());
        }
    }
}
