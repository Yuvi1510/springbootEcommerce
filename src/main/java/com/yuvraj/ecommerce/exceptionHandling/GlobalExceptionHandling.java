package com.yuvraj.ecommerce.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler({AlreadyExists.class})
    public ApiResponse handleAlreadyExists(AlreadyExists e){
        return new ApiResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
