package com.example.e_commerce.api.service;

import com.example.e_commerce.api.model.Cart;
import com.example.e_commerce.api.model.Product;
import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.repository.CartRepository;
import com.example.e_commerce.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Check if the cart item already exists
        Cart existingCartItem = cartRepository.findByProductAndUser(product, user);
        if (existingCartItem != null) {
            // Update quantity if the item already exists
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            return cartRepository.save(existingCartItem);
        }

        // Create a new cart item if it does not exist
        Cart newCartItem = new Cart();
        newCartItem.setProduct(product);
        newCartItem.setUser(user);
        newCartItem.setQuantity(quantity);

        try {
            return cartRepository.save(newCartItem);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error saving cart item: " + e.getMessage());
        }
    }

    public void removeCartItem(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(User user) {
        cartRepository.deleteAll(cartRepository.findByUser(user));
    }
}
