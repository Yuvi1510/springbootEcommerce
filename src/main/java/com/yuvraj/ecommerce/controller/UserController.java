package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import com.yuvraj.ecommerce.service.UserService;
import com.yuvraj.ecommerce.service.UserServiceImpl;
import com.yuvraj.ecommerce.service.WishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

}
