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
    public UserResponseDto addUser(@RequestBody Users user){
        Users savedUser =  userService.saveUser(user);
        return modelMapper.map(savedUser, UserResponseDto.class);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable int id){
        Users user = userService.findUserById(id);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        List<Users> users = userService.findAllUsers();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        users.forEach(u -> userResponseDtos.add(modelMapper.map(u, UserResponseDto.class)));

        return userResponseDtos;
    }

    @PutMapping("/{id}/address")
    public UserResponseDto updateAddress(@PathVariable int id, @RequestBody Address address){
        Users user = userService.updateAddress(id, address);

        return modelMapper.map(user, UserResponseDto.class);
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(@RequestBody Users user, @PathVariable int id){
        Users updatedUser = userService.updateUser(user, id);

        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable int id)  {
        return userService.deleteUser(id);
    }
}
