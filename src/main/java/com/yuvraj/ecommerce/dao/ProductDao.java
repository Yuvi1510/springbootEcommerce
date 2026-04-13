package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    List<Product> findAll(Pageable pageable);
    List<Product> findTop8By();
    Page<Product> findProductByCategoryCategoryId(int id);
}
