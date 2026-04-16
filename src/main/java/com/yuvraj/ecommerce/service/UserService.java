package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.UserResponseDto;

public interface UserService {
    User registerUser(RegistrationRequest request);
    User findUserById(int id);
}
