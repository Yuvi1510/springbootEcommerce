package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.ProductDao;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.ProductServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        List<Product> products = productService.findAll(pageable);

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/topEight")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findTop8Products();

        return ResponseEntity.ok().body(products);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable int id){
        List<Product> products = productService.getProductsByCategoryId(id);

        return ResponseEntity.ok().body(products);
    }
}
