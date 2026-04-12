package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Category;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.service.CategoryService;
import com.yuvraj.ecommerce.service.CategoryServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok().body(categories);
    }


}
