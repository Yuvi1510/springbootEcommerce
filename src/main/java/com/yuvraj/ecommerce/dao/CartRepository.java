package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query("""
        SELECT new com.yuvraj.ecommerce.responses.CartItemResponse(
        ci.cartItemId, p.name, p.images, ci.quantity, p.price, ci.total
        ) FROM CartItem ci
        JOIN ci.product p
        WHERE ci.cart.user=:user
""")
    List<CartItemResponse> getCartItems(User user);
}
