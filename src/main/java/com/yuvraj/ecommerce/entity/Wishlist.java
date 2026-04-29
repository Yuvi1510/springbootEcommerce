package com.yuvraj.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wishlistId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    public void addToWishlist(WishlistItem wishlistItem){
        wishlistItems.add(wishlistItem);
        wishlistItem.setWishlist(this);
    }

    public void removeFromWishlist(WishlistItem wishlistItem){
        wishlistItems.remove(wishlistItem);
        wishlistItem.setWishlist(null);
    }
}
