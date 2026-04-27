package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByStoreStoreId(int storeId, Pageable pageable);
    Page<Product> findProductByCategoryCategoryId(int categoryCategoryId, Pageable pageable);
}
