package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    Optional<Store> findStoreByEmail(String email);
}
