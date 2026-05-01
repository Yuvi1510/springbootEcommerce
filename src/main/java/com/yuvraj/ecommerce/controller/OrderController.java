package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.requests.OrderItemRequest;
import com.yuvraj.ecommerce.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody List<OrderItemRequest> orderItems){
        System.out.println("inside orders");

        this.orderService.createNewOrder(orderItems);

        return ResponseEntity.ok().body("");
    }
}
