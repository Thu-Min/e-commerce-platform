package com.example.e_commerce.api.controller;

import com.example.e_commerce.api.model.Category;
import com.example.e_commerce.api.service.CategoryService;
import com.example.e_commerce.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // get all categories
    @GetMapping
    public ApiResponse index() {
        List categories = categoryService.getAllCategories();
        return new ApiResponse(200, "get categories successfully", categories);
    }

    // get single category
    @GetMapping("/{id}")
    public ApiResponse show(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ApiResponse(200, "get category successfully", category);
    }

    // create new category
    @PostMapping
    public ApiResponse store(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);

        return new ApiResponse(200, "store new category successfully", null);
    }

    // update old category
    @PutMapping("/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updateCategory = categoryService.updateCategory(id, categoryDetails);

        return new ApiResponse(200, "category updated successfully", null);
    }

    // delete category
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return new ApiResponse(200, "category deleted successfully", null);
    }
}
