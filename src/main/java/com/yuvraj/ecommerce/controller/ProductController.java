package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.requests.AddProductRequest;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    public ResponseEntity<?> addProduct(@ModelAttribute AddProductRequest req){

    }
}
