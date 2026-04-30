package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import com.yuvraj.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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


        List<CartItemResponse> cartItems = this.cartService.getCartItems();

        return ResponseEntity.ok().body(cartItems);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam int productId, @RequestParam(defaultValue = "1") int quantity){

       boolean success =  cartService.addToCart(productId, quantity);


       return ResponseEntity.ok().body(new ApiResponse("Product added to cart", HttpStatus.OK));
    }
}
