package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.dto.AuthRequest;
import com.example.e_commerce.api.dto.RegisterRequest;
import com.example.e_commerce.api.repository.UserRepository;
import com.example.e_commerce.util.ApiResponse;
import com.example.e_commerce.util.JwtUtil;
import com.example.e_commerce.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            // Find user by email
            Optional<User> existingUser = userRepository.findByEmail(authRequest.getEmail());

            // Check if user exists
            if (existingUser.isEmpty()) {
                throw new Exception("User not found");
            }

            // Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(existingUser.get().getUsername(), authRequest.getPassword())
            );

            // Fetch the authenticated user's details
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate JWT token using user ID or email
            String jwt = jwtUtil.generateToken(existingUser.get().getId());

            // Return the JWT token in response
            return ResponseEntity.ok(new ApiResponse(200, "Login successful", jwt));

        } catch (BadCredentialsException e) {
            // Handle invalid credentials
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, "Incorrect email or password", null));
        } catch (Exception e) {
            // Handle other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(500, e.getMessage(), null));
        }
    }


    @PostMapping("/api/register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        // Check if the user already exists
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

        if (existingUser.isPresent()) {
            return new ApiResponse(302, "Email already exists", null);
        }

        // Create a new user
        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");

        // Save the user in the repository
        userRepository.save(user);

        // Generate JWT token using the user's ID (or email if needed)
        String jwt = jwtUtil.generateToken(user.getId());

        // Return success response along with the JWT token
        return new ApiResponse(200, "Registered successfully", jwt);
    }
}
