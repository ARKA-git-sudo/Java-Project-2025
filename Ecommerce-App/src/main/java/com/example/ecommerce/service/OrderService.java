package com.example.ecommerce.service;
import com.example.ecommerce.dto.OrderDTO;
import com.example.ecommerce.dto.OrderItemDTO;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.model.OrderItem;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.OrderRepository;
import com.example.ecommerce.repo.ProductRepository;
import com.example.ecommerce.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Inet4Address;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;
    public OrderDTO placeOrder(Long userId, Map<Long, Integer> productQuantities, double totalAmount) {
        User user= userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User not found"));

        Order order=new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("Pending");
        order.setTotalAmount(totalAmount);

        List<OrderItem> orderItems=new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS=new ArrayList<>();

        for(Map.Entry<Long, Integer> entry:productQuantities.entrySet())
        {
            Product product= productRepository.findById(entry.getKey())
                    .orElseThrow(()->new RuntimeException("Product Not found"));

            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(entry.getValue());
            orderItems.add(orderItem);

            orderItemDTOS.add(new OrderItemDTO(product.getName(),product.getPrice(),entry.getValue()));
        }

        order.setOrderItems(orderItems);
        Order saveOrder = orderRepository.save(order);
        return new OrderDTO(saveOrder.getId(), saveOrder.getTotalAmount()
                ,saveOrder.getStatus(),saveOrder.getOrderDate(),orderItemDTOS);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllOrdersWithUsers();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order orders) {
        List<OrderItemDTO> OrderItems = orders.getOrderItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity())).collect(Collectors.toList());
        return new OrderDTO(
                orders.getId(),
                orders.getTotalAmount(),
                orders.getStatus(),
                orders.getOrderDate(),
                orders.getUser()!=null ? orders.getUser().getName() : "Unknown",
                orders.getUser()!=null ? orders.getUser().getEmail() : "Unknown",
                OrderItems
        );
    }

    public List<OrderDTO> getOrderByUser(Long userId) {
        Optional<User> userOp = userRepository.findById(userId);
        if(userOp.isEmpty())
        {
            throw  new RuntimeException("user not found");
        }
        User user= userOp.get();
        List<Order> ordersList = orderRepository.findByUser(user);
        return ordersList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}