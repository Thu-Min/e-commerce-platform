package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.dto.CartRequest;
import com.example.e_commerce.api.model.Cart;
import com.example.e_commerce.api.model.User;
import com.example.e_commerce.api.service.CartService;
import com.example.e_commerce.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")

public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public ApiResponse getCartItems(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        List<Cart> cart = cartService.getCartItems(user);

        return new ApiResponse(200, "get cart successfully", cart);
    }

    @PostMapping("/add")
    public ApiResponse addToCart(@RequestBody CartRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Cart cartItem = cartService.addToCart(request.getProductId(), user, request.getQuantity());

        return new ApiResponse(200, "added to cart successfully", cartItem);
    }

    @DeleteMapping("/{cartId}")
    public ApiResponse removeCartItem(@PathVariable Long cartId) {
        cartService.removeCartItem(cartId);
        
        return new ApiResponse(200, "cart item deleted successfully", null);
    }
}
