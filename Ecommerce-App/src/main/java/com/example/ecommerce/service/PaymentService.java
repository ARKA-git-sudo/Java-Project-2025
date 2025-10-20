package com.example.ecommerce.service;

import com.example.ecommerce.model.Payment;
import com.example.ecommerce.repo.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    public String createOrder(Payment paymentDetails) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", (int) (paymentDetails.getAmount() * 100)); // Amount in paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + UUID.randomUUID().toString());

        Order razorpayOrder = client.orders.create(orderRequest);

        // Save payment details
        paymentDetails.setOrderId(razorpayOrder.get("id"));
        paymentDetails.setStatus("CREATED");
        paymentDetails.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(paymentDetails);

        System.out.println("Order created: " + razorpayOrder.get("id"));
        return razorpayOrder.toString();
    }

    public void updateOrderStatus(String paymentId, String orderId, String status) {
        Payment payment = paymentRepository.findByOrderId(orderId);

        if (payment != null) {
            payment.setPaymentId(paymentId);
            payment.setStatus(status);
            payment.setUpdatedAt(LocalDateTime.now());
            paymentRepository.save(payment);

            System.out.println("Payment updated: " + orderId + " - Status: " + status);

            // Send confirmation email if payment successful
            if ("SUCCESS".equalsIgnoreCase(status)) {
                sendConfirmationEmail(payment);
            }
        }
    }

    private void sendConfirmationEmail(Payment payment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(payment.getEmail());
            message.setSubject("Payment Confirmation - Sasta Bazar");
            message.setText(
                    "Hello " + payment.getName() + ",\n\n" +
                            "Your payment of ₹" + payment.getAmount() + " has been successfully received!\n\n" +
                            "Order Details:\n" +
                            "Order ID: " + payment.getOrderId() + "\n" +
                            "Payment ID: " + payment.getPaymentId() + "\n" +
                            "Description: " + payment.getDescription() + "\n" +
                            "Amount: ₹" + payment.getAmount() + "\n" +
                            "Status: " + payment.getStatus() + "\n" +
                            "Date: " + payment.getCreatedAt() + "\n\n" +
                            "Thank you for shopping with Sasta Bazar!\n\n" +
                            "Best Regards,\n" +
                            "Sasta Bazar Team\n" +
                            "-------------------------------------------\n" +
                            "This is an automated message. Please do not reply."
            );
            javaMailSender.send(message);
            System.out.println("Confirmation email sent to: " + payment.getEmail());
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    public Payment getPaymentByOrderId(String orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
