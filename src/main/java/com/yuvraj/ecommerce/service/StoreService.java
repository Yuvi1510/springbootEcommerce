package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Store;

import java.util.List;

public interface StoreService {
    Store registerStore(Store store);
    Store findStoreById(int id);

    Store findById(int storeId);
}
