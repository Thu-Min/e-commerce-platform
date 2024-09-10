package com.example.e_commerce.api.service;

import com.example.e_commerce.api.model.Cart;
import com.example.e_commerce.api.model.Order;
import com.example.e_commerce.api.model.OrderItem;
import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    public Order placeOrder(User user) {
        List<Cart> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("No items in the cart");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderedDate(LocalDate.now());
        order.setStatus("PENDING");
        order.setTotalAmount(100);

        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();

            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
        }

        cartService.clearCart(user);

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findByUser(user);
    }
}
