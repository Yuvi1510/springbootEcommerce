package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CarouselRepository;
import com.yuvraj.ecommerce.entity.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryByid(int id);
    Category findCategoryByName(String name);
    public List<Category> findAll();

}
