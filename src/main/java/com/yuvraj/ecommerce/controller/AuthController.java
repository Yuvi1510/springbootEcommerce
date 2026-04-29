package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.LoginRequest;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.responses.JwtResponse;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import com.yuvraj.ecommerce.service.*;
import com.yuvraj.ecommerce.utils.JwtTokenProvider;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtTokenProvider jwtTokenProvider;
private final AuthenticationManager authenticationManager;
    private final WishlistService wishlistService;
    private final CartService cartService;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager, WishlistService wishlistService, CartService cartService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.wishlistService = wishlistService;
        this.cartService = cartService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request){
        User user  = userService.registerUser(request);

        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);

        if(dto != null){
            // create wishlist
            wishlistService.createWishlist(user);

            // create cart
            cartService.createCart(user);


            return ResponseEntity.ok().body(dto);
        }

        ApiResponse res = new ApiResponse("Failed to register!", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(res);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody  LoginRequest request){
        try{
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(auth);

            String token = jwtTokenProvider.generateToken(authentication);

            return ResponseEntity.ok().body(new JwtResponse(token));
        }catch (Exception e){
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
