package com.example.e_commerce.api.repository;

import com.example.e_commerce.api.model.Cart;
import com.example.e_commerce.api.model.Product;
import com.example.e_commerce.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUser(User user);

    Cart findByProductAndUser(Product product, User user);
}
