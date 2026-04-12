package com.yuvraj.ecommerce.security;

import com.yuvraj.ecommerce.service.CustomUserPrincipal;
import com.yuvraj.ecommerce.service.UserService;
import com.yuvraj.ecommerce.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter  extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    @Autowired
    public JwtSecurityFilter(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/auth");
//        return request.getServletPath().startsWith("/");
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtering: " + request.getServletPath());
        try{
            String token = getTokenFromRequest(request);

            if(token != null && jwtTokenProvider.validateToken(token)){
                // extract username/email from  request token
                String username = jwtTokenProvider.getUsernameFromToken(token);
                System.out.println("Username " + username);
                // load user from db
                // loadUserByUsername returns and object of CustomUserPrincipal which implements UserDetails interface
                UserDetails userDetails = userService.loadUserByUsername(username);

                System.out.println(userDetails);

                // create Authentication object
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // password not required fot jwt filter
                        userDetails.getAuthorities() // roles
                );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("success");
            }

            filterChain.doFilter(request,response);
        } catch (Exception e) {
            logger.error("Can not set user authentication: "+ e);
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(bearerToken!= null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }

        return  null;
    }
}
