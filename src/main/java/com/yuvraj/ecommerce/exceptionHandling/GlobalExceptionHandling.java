package com.yuvraj.ecommerce.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler({AlreadyExists.class, NotFountException.class})
    public ApiResponse handleAlreadyExistsAndNotFound(RuntimeException e){
        String msg = e.getMessage();
        HttpStatus status = null;
        if(e instanceof  AlreadyExists){
            status = HttpStatus.CONFLICT;
        } else if (e instanceof NotFountException) {
            status = HttpStatus.NOT_FOUND;
        }
        return new ApiResponse(msg, status);
    }
}
