package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.ProductImage;
import com.yuvraj.ecommerce.requests.AddProductRequest;

public interface ProductService {
    Product addProduct(AddProductRequest req);
}
