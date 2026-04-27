package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Category;
import com.yuvraj.ecommerce.service.CategoryService;
import com.yuvraj.ecommerce.service.CategoryServiceImpl;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories(){
        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok().body(categories);
    }
}
