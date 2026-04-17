package com.yuvraj.ecommerce.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Utils {
//    @Bean
//    public static BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    public static String generateSlug(String name, int id) {
        String slug = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")   // remove special chars
                .trim()
                .replaceAll("\\s+", "-");         // spaces → hyphen

        return slug + "-" + id;
    }
}
