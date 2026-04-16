package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public User registerUser(RegistrationRequest request) {
        User user = modelMapper.map(request, User.class);
        user.setEmailVerified(true);
        user.setActive(true);
        user.setLocked(false);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());

        return userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        Optional<User> optional =  userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NotFountException("User not found with id: "+ id));

        return user;
    }
}
