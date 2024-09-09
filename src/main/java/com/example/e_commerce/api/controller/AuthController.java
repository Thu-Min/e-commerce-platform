package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.dto.AuthRequest;
import com.example.e_commerce.api.dto.AuthResponse;
import com.example.e_commerce.api.dto.RegisterRequest;
import com.example.e_commerce.api.repository.UserRepository;
import com.example.e_commerce.util.ApiResponse;
import com.example.e_commerce.util.JwtUtil;
import com.example.e_commerce.api.model.User;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @PostMapping("/api/login")
//    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws Exception {
//        try {
//            Optional<User> existingUser = userRepository.findByEmail(authRequest.getEmail());
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(existingUser.get().getName(), authRequest.getPassword())
//            );
//
//            System.out.println(authentication);
//
////            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
////            final User user = (User) userDetails;
////
////            final String jwt = jwtUtil.generateToken(user.getId());
////
////            return ResponseEntity.ok(new AuthResponse(jwt));
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//            return ResponseEntity.ok(new ApiResponse(200, "login success", jwt));
//        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect email or password", e);
//        }
//    }

    @PostMapping("/api/register")
    public ApiResponse register(@RequestBody RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

        if(existingUser.isPresent()) {
            return new ApiResponse(302, "Email is already exist", null);
        }

        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()));
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return new ApiResponse(200, "registered successfully", null);
    }

}
