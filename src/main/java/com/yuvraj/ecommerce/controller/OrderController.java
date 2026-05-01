package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.requests.OrderItemRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody List<OrderItemRequest> orderItems){
        System.out.println("inside orders");
        System.out.println(orderItems);
        for(OrderItemRequest oi : orderItems){
            System.out.println("inside loop");
            System.out.println(oi.getProductId());
        }

        return ResponseEntity.ok().body("");
    }
}
