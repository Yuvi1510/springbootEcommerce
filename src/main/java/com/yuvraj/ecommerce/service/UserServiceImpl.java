package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import com.yuvraj.ecommerce.requests.RegistrationRequest;
import com.yuvraj.ecommerce.responses.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }


    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> optional = this.userRepository.findUserByEmail(request.getEmail());
        if(optional.isPresent()){
            throw new AlreadyExists("User", "email", request.getEmail());
        }


        User user = modelMapper.map(request, User.class);
        user.setEmailVerified(true);
        user.setActive(true);
        user.setLocked(false);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        Optional<User> optional =  userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NotFountException("User not found with id: "+ id));

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = this.userRepository.findUserByEmail(username)
               .orElseThrow(() -> new NotFountException("User now found with this email: "+ username));

       return user;

    }
}
