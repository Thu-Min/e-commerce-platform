package com.example.e_commerce.config;

import com.example.e_commerce.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // Find user by UUID
        return userRepository.findById(String.valueOf(UUID.fromString(userId)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
    }
}
