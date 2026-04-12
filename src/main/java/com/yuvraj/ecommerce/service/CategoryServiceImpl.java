package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CategoryDao;
import com.yuvraj.ecommerce.dao.ProductDao;
import com.yuvraj.ecommerce.entity.Category;
import com.yuvraj.ecommerce.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDao categoryDao;
    private final ProductDao productDao;

    public CategoryServiceImpl(CategoryDao categoryDao, ProductDao productDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }


}
