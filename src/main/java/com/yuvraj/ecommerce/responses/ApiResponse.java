package com.yuvraj.ecommerce.responses;

import org.springframework.http.HttpStatus;

public class ApiResponse {
    public String message;
    public HttpStatus httpStatus;

    public ApiResponse(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
