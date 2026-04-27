package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.ProductImage;
import com.yuvraj.ecommerce.requests.AddProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product findProductById(int id);
    Page<Product> findAllProducts(Pageable pageable);
    Page<Product> findProductsByCategoryId(int id, Pageable pageable);
    Product addProduct(AddProductRequest req);
    List<Product> findProductsByStore(int id);
}
