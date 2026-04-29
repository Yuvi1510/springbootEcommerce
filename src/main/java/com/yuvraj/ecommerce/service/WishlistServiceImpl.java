package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.WishlistItemRepository;
import com.yuvraj.ecommerce.dao.WishlistRepository;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.entity.Wishlist;
import com.yuvraj.ecommerce.entity.WishlistItem;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService{
    private final WishlistItemRepository wishlistItemRepository;
    private final WishlistRepository wishlistRepository;

    public WishlistServiceImpl(WishlistItemRepository wishlistItemRepository, WishlistRepository wishlistRepository) {
        this.wishlistItemRepository = wishlistItemRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public boolean createWishlist(User user) {
        try{
            Wishlist wishlist = new Wishlist();
           user.setWishlist(wishlist);
           wishlist.setUser(user);

            this.wishlistRepository.save(wishlist);

            return true;
        } catch (RuntimeException e) {
            System.out.println("Error creating wishlist");
            return false;
        }
    }


    @Override
    public Wishlist findByUser(User user) {
        return this.wishlistRepository.findByUser(user).orElseThrow(() -> new NotFountException("Wishlist not found for the user"));
    }

    @Override
    public Wishlist addToWishlist(Wishlist wishlist, WishlistItem wishlistItem) {
        wishlist.addToWishlist(wishlistItem);

       Wishlist savedWishlist =  this.wishlistRepository.save(wishlist);

       return savedWishlist;
    }

    @Override
    public boolean removeFromWishlist(Wishlist wishlist, WishlistItem wishlistItem) {
        return false;
    }

    @Override
    public List<WishlistItem> getWishlistItems(User user) {
        return this.wishlistRepository.findWishlistItems(user);
    }


}
