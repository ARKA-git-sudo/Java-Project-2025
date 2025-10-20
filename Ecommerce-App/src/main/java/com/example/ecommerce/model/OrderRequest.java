package com.example.ecommerce.model;

import jakarta.persistence.Entity;
import org.springframework.data.repository.cdi.Eager;

import java.util.Map;

public class OrderRequest {

    private Map<Long,Integer> productQuantities;
    private double totalAmount;

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}



