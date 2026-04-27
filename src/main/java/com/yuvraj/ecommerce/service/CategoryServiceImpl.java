package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CategoryRepository;
import com.yuvraj.ecommerce.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public Category findCategoryByName(String name) {
        Category cat = categoryRepository.findByName(name);
        return cat;
    }

    @Override
    public List<Category> findAll(){
        List<Category> categories = categoryRepository.findAll();

        return  categories;
    }
}
