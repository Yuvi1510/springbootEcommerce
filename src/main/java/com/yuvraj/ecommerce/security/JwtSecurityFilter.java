package com.yuvraj.ecommerce.security;

import com.yuvraj.ecommerce.service.UserServiceImpl;
import com.yuvraj.ecommerce.utils.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
private final UserDetailsService userService;

    @Autowired
    public JwtSecurityFilter(JwtTokenProvider jwtTokenProvider, UserServiceImpl userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        System.out.println(request.getServletPath());
//        System.out.println(request.getServletPath().startsWith("/auth"));
//        return request.getServletPath().startsWith("/auth");
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        System.out.println(path);

        // Skip JWT validation for auth endpoints
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            System.out.println("skipping "+ path);
            return;
        }

        try{
        String token = getTokenFromRequest(request);

        if(token != null && jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsernameFromToken(token);

            UserDetails userDetails = this.userService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            filterChain.doFilter(request, response);
        }

    }



    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }

        return null;
    }
}
