package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.entity.Order;
import com.yuvraj.ecommerce.requests.OrderItemRequest;

import java.util.List;

public interface OrderService {
    Order createNewOrder(List<OrderItemRequest> orderItems);
}
