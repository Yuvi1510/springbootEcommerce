package com.yuvraj.ecommerce.enums;

public enum PaymentStatus {

    PENDING,        // Payment initiated but not completed
    SUCCESS,        // Payment completed successfully
    FAILED,         // Payment failed
    CANCELLED,      // User cancelled payment
    REFUNDED,       // Money refunded to user
    EXPIRED         // Payment session expired
}