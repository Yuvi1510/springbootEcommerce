package com.yuvraj.ecommerce.exceptionHandling;

import com.yuvraj.ecommerce.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler({AlreadyExists.class, NotFountException.class})
    public ResponseEntity<ApiResponse> handleAlreadyExistsAndNotFound(RuntimeException e){
        String msg = e.getMessage();
        HttpStatus status = null;
        if(e instanceof  AlreadyExists){
            status = HttpStatus.CONFLICT;
        } else if (e instanceof NotFountException) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status).body(new ApiResponse(msg, status));
    }

    @ExceptionHandler({UnAuthenticatedException.class})
    public ResponseEntity<ApiResponse> handleAuthenticationAndAuthorizationException(RuntimeException e){
        String msg = e.getMessage();
        HttpStatus status = null;

        if(e instanceof UnAuthenticatedException){
            status = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity.badRequest().body(new ApiResponse(msg, status));
    }
}
