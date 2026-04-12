package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findTop8By();
    List<Product> findProductByCategoryCategoryId(int id);
}
