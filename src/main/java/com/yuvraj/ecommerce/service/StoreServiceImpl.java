package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.StoreRepository;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService{
    private final StoreRepository storeRepository;

    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Store registerStore(Store store) {
        Optional<Store> existingStore = storeRepository.findStoreByEmail(store.getEmail());
        existingStore.ifPresent((s) -> {
            throw new AlreadyExists("Store", "email", store.getEmail());
        });
        store.setCreatedAt(LocalDate.now());
        store.setUpdatedAt(LocalDate.now());
        return storeRepository.save(store);
    }

    @Override
    public Store findStoreById(int id) {
        Optional<Store> optional = storeRepository.findStoreByStoreId(id);
        return optional.orElse(null);

    }

    @Override
    public Store findById(int storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }
}
