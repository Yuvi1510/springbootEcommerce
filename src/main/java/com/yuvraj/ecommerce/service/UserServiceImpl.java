package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.UserDao;
import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.responses.ApiResponse;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import jakarta.transaction.Transactional;
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

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    @Override
    @Transactional
    public Users saveUser(Users user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDao.saveUser(user);
    }

    @Override
    public Users findUserByEmail(String email) {
        Users user = userDao.findUserByEmail(email);
        if(user == null){
            throw new NotFountException("User not found with email: "+ email);
        }
        return user;
    }

    @Override
    public Users findUserById(int id)  {
        Users user = userDao.findUserById(id);

        if(user == null){
            throw new NotFountException("User not found with id: "+id);
        }
         return user;
    }

    @Override
    public List<Users> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    @Transactional
    public Users updateUser(Users user, int id) {
        return userDao.updateUser(user, id);
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
