package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.requests.AddProductRequest;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@ModelAttribute AddProductRequest req){

        System.out.println(req);
        Product product = productService.addProduct(req);
        return ResponseEntity.ok().body(product);
    }
}
