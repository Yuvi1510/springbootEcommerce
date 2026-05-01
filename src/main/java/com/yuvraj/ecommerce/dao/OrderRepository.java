package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
