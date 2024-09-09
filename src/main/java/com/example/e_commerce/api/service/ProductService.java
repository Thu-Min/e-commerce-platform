package com.example.e_commerce.api.service;

import com.example.e_commerce.api.dto.ProductRequest;
import com.example.e_commerce.api.model.Category;
import com.example.e_commerce.api.model.Product;
import com.example.e_commerce.api.repository.CategoryRepository;
import com.example.e_commerce.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(ProductRequest productDto) {
        Product product = new Product();

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest productDetails) {
        Product product = getProductById(id);

        Category category = categoryRepository.findById(productDetails.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDetails.getName());
        product.setCategory(category);
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setStock(productDetails.getStock());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = getProductById(id);

        productRepository.delete(product);
    }
}
