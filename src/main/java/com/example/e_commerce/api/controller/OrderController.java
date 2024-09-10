package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.model.Order;
import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.service.OrderService;
import com.example.e_commerce.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ApiResponse getUserOrders(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Order> orders = orderService.getUserOrders(user);

        return new ApiResponse(200, "get order successfully", orders);
    }

    @PostMapping("/place")
    public ApiResponse placeOrder(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Order order = orderService.placeOrder(user);

        return new ApiResponse(200, "placed order successfully", order);
    }
}
