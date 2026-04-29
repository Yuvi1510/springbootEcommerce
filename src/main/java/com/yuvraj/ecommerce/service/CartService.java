package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.CartItemResponse;

import java.util.List;

public interface CartService {
    Cart createCart(User user);
    boolean addToCart(Cart cart, CartItem cartItem);
    boolean removeFromCart(Cart cart, CartItem cartItem);
    CartItem updateQuantity(CartItem cartItem, int quantity);
    public List<CartItemResponse> getCartItems(User user);
}
