package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
