package com.example.ecommerce.controller;

import com.example.ecommerce.model.Payment;
import com.example.ecommerce.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody Payment paymentDetails){
        try {
            String razorpayOrder = paymentService.createOrder(paymentDetails);
            return ResponseEntity.ok(razorpayOrder);
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to create order: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Error creating order\"}");
        }
    }

    @PostMapping("/update-order")
    public ResponseEntity<?> updateOrderStatus(
            @RequestParam String paymentId,
            @RequestParam String orderId,
            @RequestParam String status){
        try {
            paymentService.updateOrderStatus(paymentId, orderId, status);
            return ResponseEntity.ok("{\"success\": true, \"message\": \"Order updated successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"success\": false, \"message\": \"Error updating order\"}");
        }
    }
}