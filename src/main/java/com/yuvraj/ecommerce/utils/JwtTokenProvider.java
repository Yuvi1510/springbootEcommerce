package com.yuvraj.ecommerce.utils;

import com.yuvraj.ecommerce.service.CustomUserPrincipal;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpiration}")
    private long jwtExpirationMs;

    public String generateToken(Authentication authentication){

//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
// when we use UserDetails interface we won't be able to add other infos like user id
        // in the claim while generating jwt so we need to make a custom
        // user principal that implements UserDetails interface and add a
        // method to get infos like getId()

        CustomUserPrincipal userDetails = (CustomUserPrincipal) authentication.getPrincipal();

       Date now = new Date();
       Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .claim("roles",userDetails.getAuthorities())
                .claim("userId", userDetails.getUserId())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return  claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(jwtSecret)
                    .build().parseClaimsJws(token);
        }catch (SignatureException e){
            logger.error("Invalid jwt signature");
        }catch (MalformedJwtException e){
            logger.error("Invalid jwt token");
        }catch (ExpiredJwtException e){
            logger.error("Expired JWT exception");
        }catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT token");
        }catch (IllegalArgumentException e){
            logger.error("JWT claims string is empty");
        }

        return false;
    }

}
