package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
