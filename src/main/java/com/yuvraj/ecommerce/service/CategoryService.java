package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Category;
import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService  {
    List<Category> getAllCategories();
}
