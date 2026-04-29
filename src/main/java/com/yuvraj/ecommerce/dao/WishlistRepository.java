package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.entity.Wishlist;
import com.yuvraj.ecommerce.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    Optional<Wishlist> findByUser(User user);

    @Query("""
        SELECT wi from WishlistItem wi
        where wi.wishlist.user=:user
""")
    List<WishlistItem> findWishlistItems(User user);
}
