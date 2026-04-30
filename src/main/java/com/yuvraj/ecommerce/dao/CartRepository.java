package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
        List<CartItem> getCartItemsByCartId(int cartId);

        @Query("SELECT c FROM Cart c WHERE c.user.userId = :userId")
        Cart findByUserId(@Param("userId") int userId);
}
