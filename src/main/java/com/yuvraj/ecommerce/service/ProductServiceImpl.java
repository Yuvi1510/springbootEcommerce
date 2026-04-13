package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.ProductDao;
import com.yuvraj.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public List<Product> findAll(Pageable pageable) {
        return productDao.findAll(pageable).getContent();
    }

    @Override
    public List<Product> findTop8Products() {
       List<Product> products = productDao.findTop8By();
       return products;
    }

    @Override
    public List<Product> getProductsByCategoryId(int id) {
        Page<Product> products = productDao.findProductByCategoryCategoryId(id);
        return products.getContent();
    }
}
