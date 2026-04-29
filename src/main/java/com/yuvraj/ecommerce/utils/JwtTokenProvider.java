package com.yuvraj.ecommerce.utils;

import com.yuvraj.ecommerce.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final long jwtExpiration;

    private static Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    public JwtTokenProvider(
            @Value("${app.jwtSecret}") String jwtSecret,
            @Value("${app.jwtExpiration}") long jwtExpiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.jwtExpiration = jwtExpiration;
    }



    public String generateToken(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpiration);

//        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
//        Key key = Keys.hmacShaKeyFor(keyBytes);
//        jwtBuilder.signWith(key); //or signWith(Key, SignatureAlgorithm)



        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .claim("roles", userDetails.getAuthorities())
                .claim("userId", ((User)userDetails).getUserId())
                .signWith(key)
                .compact();
    }


    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();

    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
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
