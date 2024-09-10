package com.example.e_commerce.api.service;

import com.example.e_commerce.api.model.Cart;
import com.example.e_commerce.api.model.Product;
import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.repository.CartRepository;
import com.example.e_commerce.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Cart> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    public Cart addToCart(Long productId, User user, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cartItem = new Cart();
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(quantity);

        return cartRepository.save(cartItem);
    }

    public void removeCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(User user) {
        cartRepository.deleteAll(cartRepository.findByUser(user));
    }
}
