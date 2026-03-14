package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.UserDao;
import com.yuvraj.ecommerce.dto.UserResponseDto;
import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import com.yuvraj.ecommerce.exceptionHandling.ApiResponse;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import com.yuvraj.ecommerce.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> addUser(@RequestBody Users user){
        Users savedUser =  userService.saveUser(user);
        UserResponseDto savedUserDto = modelMapper.map(savedUser, UserResponseDto.class);
        return ResponseEntity.ok().body(savedUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable int id){
        Users user = userService.findUserById(id);

        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return ResponseEntity.ok().body(userDto);

    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        List<Users> users = userService.findAllUsers();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(u -> userResponseDtos.add(modelMapper.map(u, UserResponseDto.class)));

        return ResponseEntity.ok().body(userResponseDtos);
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<UserResponseDto> updateAddress(@PathVariable int id, @RequestBody Address address){
        Users user = userService.updateAddress(id, address);

        UserResponseDto userDto =  modelMapper.map(user, UserResponseDto.class);

        return ResponseEntity.ok().body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody Users user, @PathVariable int id){
        Users updatedUser = userService.updateUser(user, id);

        UserResponseDto userDto = modelMapper.map(updatedUser, UserResponseDto.class);

        return ResponseEntity.ok().body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable int id)  {
        ApiResponse apiResponse =  userService.deleteUser(id);
        return ResponseEntity.ok().body(apiResponse);
    }
}
