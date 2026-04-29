package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import com.yuvraj.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<?> getAllCartItems(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CartItemResponse> cartItems = this.cartService.getCartItems(user);

        return ResponseEntity.ok().body(cartItems);
    }
}
