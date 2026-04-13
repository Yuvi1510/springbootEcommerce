package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> findAll(Pageable pageable);
    List<Product> findTop8Products();
    List<Product> getProductsByCategoryId(int id);

}
