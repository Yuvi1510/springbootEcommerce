package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CartItemRepository;
import com.yuvraj.ecommerce.dao.CartRepository;
import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.exceptionHandling.UnAuthenticatedException;
import com.yuvraj.ecommerce.responses.CartItemResponse;
import com.yuvraj.ecommerce.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
   private final ProductService productService;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @Override
    public Cart createCart(User user) {
       Cart cart = new Cart();
       cart.setUser(user);

        return this.cartRepository.save(cart);
    }

    @Override
    public List<CartItemResponse> getCartItems(User user){

        List<CartItem> cartItems =  this.cartRepository.getCartItemsByCartId(user.getCart().getCartId());

        List<CartItemResponse> res = cartItems.stream()
                .map(i -> new CartItemResponse(
                        i.getCartItemId(),
                        i.getProduct().getName(),
                        i.getProduct().getImages(),
                        i.getQuantity(),
                        i.getProduct().getPrice(),
                        i.getTotal()
                )).collect(Collectors.toList());

        return res;
    }

    @Override
    @Transactional
    public boolean addToCart(int productId, int quantity) {
         User authenticatedUser = SecurityUtils.getCurrentUser();
        if(authenticatedUser == null){
            throw new UnAuthenticatedException("Please login first!");
        }

        Optional<User> optional = userRepository.findUserByEmail(authenticatedUser.getEmail());

        User user = optional.orElseThrow(()->   new UnAuthenticatedException("Please login first!"));

        System.out.println(user.getUsername());

        Cart cart = this.cartRepository.findByUserId(user.getUserId());
        System.out.println(cart.getCartId());
        Product product = productService.findProductById(productId);
        CartItem cartItem = new CartItem(product, quantity);
        cart.addCartItem(cartItem);

            cartRepository.save(cart);
            return true;

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
