package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.CartItemRepository;
import com.yuvraj.ecommerce.dao.CartRepository;
import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.Cart;
import com.yuvraj.ecommerce.entity.CartItem;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
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
    private final SecurityUtils securityUtils;


    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductService productService, SecurityUtils securityUtils) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
        this.securityUtils = securityUtils;
    }

    @Override
    public Cart createCart(User user) {
       Cart cart = new Cart();
       cart.setUser(user);

        return this.cartRepository.save(cart);
    }

    @Override
    public List<CartItemResponse> getCartItems(){

        User user = securityUtils.getCurrentUser();

        List<CartItem> cartItems =  user.getCart().getCartItems();

        List<CartItemResponse> res = cartItems.stream()
                .map(i -> new CartItemResponse(
                        i.getCartItemId(),
                        i.getProduct().getName(),
                        i.getProduct().getImages(),
                        i.getQuantity(),
                        i.getProduct().getPrice(),
                        i.getTotal(),
                        i.getCart().getCartId()
                )).collect(Collectors.toList());

        return res;
    }

    @Override
    @Transactional
    public boolean addToCart(int productId, int quantity) {
         User user = securityUtils.getCurrentUser();

        // get the cart of the user
        Cart cart = this.cartRepository.findByUserId(user.getUserId());

        // get the cart items and check of the item already exists
        List<CartItem> cartItems = cart.getCartItems();
        Product product = productService.findProductById(productId);

        for(CartItem cartItem: cartItems){
            // check is product already exists
            if(cartItem.getProduct().getProductId() == productId){
                // check if the product is not added again from add to cart
                // quantity == 1 only if add to cart is clicked
                if(quantity != 1){
                    // if the cart item already exists then don't add just update the quaantity
                    cartItem.setQuantity(quantity);

                    // update the total as well
                    cartItem.setTotal(quantity * cartItem.getProduct().getPrice());

                    // save the cart with updated cartItem
                    cartRepository.save(cart);
                }
                return true;
            }
        }

        // if cartitem is not present then create new one
        // add to cart and save the cart
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
