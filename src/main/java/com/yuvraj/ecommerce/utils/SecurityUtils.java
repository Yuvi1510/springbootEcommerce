package com.yuvraj.ecommerce.utils;

import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.exceptionHandling.UnAuthenticatedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Optional;


@Component
public class SecurityUtils {

    private final  UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
            !authentication.isAuthenticated() ||
            !(authentication.getPrincipal() instanceof User)) {
            return null;
        }

        User authenticatedUser =  (User) authentication.getPrincipal();

        Optional<User> optional = userRepository.findUserByEmail(authenticatedUser.getEmail());

        return optional.orElseThrow(() -> new UnAuthenticatedException("User is not authenticated"));
    }
}