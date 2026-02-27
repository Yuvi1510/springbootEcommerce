package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.ApiResponse;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;

import java.util.List;

public interface UserDao {
    Users saveUser(Users user);
    Users findUserByEmail(String email);
    Users findUserById(int id) throws NotFountException;
    List<Users> findAllUsers();
    Users updateAddress(int userId, Address address);
    Users updateUser(Users user);
    void deleteUser(Users user);
}