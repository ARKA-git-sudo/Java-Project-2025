package com.example.ecommerce.repo;


import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;



import java.util.List;



public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o JOIN FETCH o.user")
    List<Order> findAllOrdersWithUsers();

    List<Order> findByUser(User user);
}





