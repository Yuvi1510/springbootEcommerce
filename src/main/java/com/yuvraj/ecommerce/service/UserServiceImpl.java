package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.UserDao;
import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import com.yuvraj.ecommerce.exceptionHandling.ApiResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public Users saveUser(Users user) {
        if(findUserByEmail(user.getEmail()) != null){
            try {
                throw new AlreadyExists("User","email",user.getEmail());
            } catch (AlreadyExists e) {
                throw new RuntimeException(e);
            }
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.saveUser(user);
    }

    @Override
    public Users findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public Users findUserById(int id)  {
        return userDao.findUserById(id);

    }

    @Override
    public List<Users> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    @Transactional
    public Users updateUser(Users user, int id) {
        user.setUserId(id);
        return userDao.updateUser(user);
    }

    @Override
    @Transactional
    public Users updateAddress(int id, Address address) {
        return userDao.updateAddress(id, address);
    }

    @Override
    @Transactional
    public ApiResponse deleteUser(int id) {
        Users user = userDao.findUserById(id);
        return new ApiResponse("User successfully deleted", HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findUserByEmail(username);

        return new CustomUserPrincipal(user);
    }
}
