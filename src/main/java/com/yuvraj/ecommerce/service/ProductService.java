package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findTop8Products();
    List<Product> getProductsByCategoryId(int id);

}
