package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
}
