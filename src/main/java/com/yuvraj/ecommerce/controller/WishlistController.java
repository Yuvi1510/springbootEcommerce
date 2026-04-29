package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.entity.Wishlist;
import com.yuvraj.ecommerce.entity.WishlistItem;
import com.yuvraj.ecommerce.service.ProductService;
import com.yuvraj.ecommerce.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final ProductService productService;

    @Autowired
    public WishlistController(WishlistService wishlistService, ProductService productService) {
        this.wishlistService = wishlistService;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getWishlistItems(){
        User user = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        List<WishlistItem> items = this.wishlistService.getWishlistItems(user);

        return ResponseEntity.ok().body(items);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestParam int productId){
        Product product =  productService.findProductById(productId);

        User user = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        Wishlist wishlist = wishlistService.findByUser(user);

        WishlistItem wishlistItem = new WishlistItem(product);

       Wishlist savedWishlist = this.wishlistService.addToWishlist(wishlist, wishlistItem);

       return ResponseEntity.ok().body(savedWishlist);

    }
}
