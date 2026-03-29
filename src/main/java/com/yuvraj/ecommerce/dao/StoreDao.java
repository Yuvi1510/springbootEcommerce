package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Store;

public interface StoreDao {
    Store addStore(Store store);
    Store findStoreById(int id);
    Store findStoreByName(String name);
    Store findStoreByEmail(String email);
    Store findStoreByUserId(int userId);
}
