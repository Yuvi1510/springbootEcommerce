package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.entity.Wishlist;
import com.yuvraj.ecommerce.entity.WishlistItem;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.util.List;

public interface WishlistService {
    boolean createWishlist(User user);
    Wishlist findByUser(User user);
    Wishlist addToWishlist(Wishlist wishlist, WishlistItem wishlistItem);
    boolean removeFromWishlist(Wishlist wishlist, WishlistItem wishlistItem);
    List<WishlistItem> getWishlistItems(User user);
}
