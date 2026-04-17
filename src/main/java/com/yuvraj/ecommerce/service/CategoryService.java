package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CarouselRepository;
import com.yuvraj.ecommerce.entity.Category;

public interface CategoryService {
    Category getCategoryByid(int id);
}
