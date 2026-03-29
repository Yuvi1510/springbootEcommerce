package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.responses.ApiResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    Users saveUser(Users user) ;
    Users findUserByEmail(String email);
    Users findUserById(int id);
    List<Users> findAllUsers();
    Users updateUser(Users user, int id);
    Users updateAddress(int userId, Address address);
    ApiResponse deleteUser(int id) ;

}
