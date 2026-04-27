package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerUser(RegistrationRequest request);
    User findUserById(int id);
}
