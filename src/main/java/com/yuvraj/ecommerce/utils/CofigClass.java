package com.yuvraj.ecommerce.utils;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CofigClass {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
