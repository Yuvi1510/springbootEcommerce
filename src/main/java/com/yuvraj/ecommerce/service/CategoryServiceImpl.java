package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CategoryRepository;
import com.yuvraj.ecommerce.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryByid(int id) {
        return categoryRepository.findById(id).get();
    }
}
