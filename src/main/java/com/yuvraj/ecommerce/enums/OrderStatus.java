package com.yuvraj.ecommerce.enums;

public enum OrderStatus {
    PENDING,        // Order created but not confirmed yet
    CONFIRMED,      // Order confirmed (stock reserved, valid order)

    PROCESSING,     // Preparing (packing, warehouse handling)
    SHIPPED,        // Handed to courier
    OUT_FOR_DELIVERY, // Courier is delivering

    DELIVERED,      // Successfully delivered

    CANCELLED,      // Cancelled by user/admin
    RETURN_REQUESTED, // User requested return
    RETURNED,       // Returned successfully

    REFUNDED        // Payment refunded
}
