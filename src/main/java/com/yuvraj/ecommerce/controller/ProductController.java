package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.ProductImage;
import com.yuvraj.ecommerce.requests.AddProductRequest;
import com.yuvraj.ecommerce.responses.PageResponse;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable int id){
        Product product =  productService.findProductById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<?> findProductsByCategory(
            @PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("name").ascending().and(
                        Sort.by("price").descending()
                ));

        Page<Product> products = productService.findProductsByCategoryId(id, pageable);

        PageResponse<Product> response = new PageResponse<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> findAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending()
                .and(Sort.by("price").descending())
        );
        Page<Product> products =  productService.findAllProducts(pageable);

        return ResponseEntity.ok().body(products);
    }


}
