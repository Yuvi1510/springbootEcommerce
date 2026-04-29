package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CartItemRepository;
import com.yuvraj.ecommerce.dao.CartRepository;
import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart createCart(User user) {
       Cart cart = new Cart();
       cart.setUser(user);

        return this.cartRepository.save(cart);
    }

    @Override
    public List<CartItemResponse> getCartItems(User user){
        return this.cartRepository.getCartItems(user);
    }

    @Override
    public boolean addToCart(Cart cart, CartItem cartItem) {
        cart.addCartItem(cartItem);
        try{
            cartRepository.save(cart);
            return true;
        }catch (RuntimeException e){
            System.out.println("Error adding to cart");
            return false;
        }
    }

    @Override
    public boolean removeFromCart(Cart cart, CartItem cartItem) {
       cart.removeCartItem(cartItem);

       try{
           cartRepository.save(cart);
           return true;
       }catch (RuntimeException e){
           System.out.println("Error removing cart item");
           return false;
       }
    }

    @Override
    public CartItem updateQuantity(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);

        return this.cartItemRepository.save(cartItem);
    }
}
