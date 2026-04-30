package com.yuvraj.ecommerce.exceptionHandling;

public class UnAuthenticatedException extends RuntimeException {
    public UnAuthenticatedException(String message) {
        super(message);
    }
}
