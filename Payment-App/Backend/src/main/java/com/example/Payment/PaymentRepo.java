package com.example.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<MyPayment, Long> {
    MyPayment findByOrderId(String orderId);
}
