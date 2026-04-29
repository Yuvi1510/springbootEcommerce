package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Integer> {

}
