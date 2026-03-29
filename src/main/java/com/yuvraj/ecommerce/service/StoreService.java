package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Store;

public interface StoreService {
    Store addStore(Store store);
    Store findStoreById(int id);
    Store findStoreByName(String name);
    Store findStoreByEmail(String email);
    Store findStoreByUserId(int userId);
}
