package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.StoreDao;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService{
    private final StoreDao storeDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public StoreServiceImpl(StoreDao storeDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.storeDao = storeDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public Store addStore(Store store) {
        store.setPassword(bCryptPasswordEncoder.encode(store.getPassword()));
        return storeDao.addStore(store);
    }

    @Override
    public Store findStoreById(int id) {
        Store store = storeDao.findStoreById(id);

        if(store == null){
            throw new NotFountException("Store not found with id: "+ id);
        }

        return store;
    }

    @Override
    public Store findStoreByName(String name) {
        Store store = storeDao.findStoreByName(name);

        if(store == null){
            throw new NotFountException("Store not found with name: "+ name);
        }

        return store;
    }

    @Override
    public Store findStoreByEmail(String email) {
        Store store = storeDao.findStoreByEmail(email);
        if(store == null){
            throw new NotFountException("Store now found with email: "+ email);
        }

        return store;
    }
}
