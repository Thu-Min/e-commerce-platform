package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.dto.ProductRequest;
import com.example.e_commerce.api.model.Product;
import com.example.e_commerce.api.service.ProductService;
import com.example.e_commerce.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    // get all products
    @GetMapping
    public ApiResponse index() {
        List products = productService.getAllProducts();

        return new ApiResponse(200, "get products successfully", products);
    }

    // get single product
    @GetMapping("/{id}")
    public ApiResponse show(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        return new ApiResponse(200, "get product successfully", product);
    }

    // create new product
    @PostMapping
    public ApiResponse store(@RequestBody ProductRequest product) {
        Product newProduct = productService.createProduct(product);

        return new ApiResponse(200, "store new product successfully", null);
    }

    // update old product
    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody ProductRequest product) {
        productService.updateProduct(id, product);

        return new ApiResponse(200, "product updated successfully", null);
    }

    // delete product
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        productService.deleteProduct(id);

        return new ApiResponse(200, "product deleted successfully", null);
    }
}
