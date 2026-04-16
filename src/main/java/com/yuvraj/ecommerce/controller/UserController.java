package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import com.yuvraj.ecommerce.service.UserService;
import com.yuvraj.ecommerce.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserServiceImpl userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request){
        User user  = userService.registerUser(request);

        UserResponseDto dto = modelMapper.map(user, UserResponseDto.class);

        if(dto != null){
            return ResponseEntity.ok().body(dto);
        }

        ApiResponse res = new ApiResponse("Failed to register!", HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(res);
    }
}
