package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {

}
