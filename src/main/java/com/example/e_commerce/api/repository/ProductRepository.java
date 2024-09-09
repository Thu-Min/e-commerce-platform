package com.example.e_commerce.api.repository;

import com.example.e_commerce.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
