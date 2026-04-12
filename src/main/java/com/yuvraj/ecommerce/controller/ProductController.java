package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.ProductDao;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
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
