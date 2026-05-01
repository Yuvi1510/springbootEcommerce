package com.yuvraj.ecommerce.service;

import com.yuvraj.ecommerce.dao.OrderRepository;
import com.yuvraj.ecommerce.dao.UserRepository;
import com.yuvraj.ecommerce.entity.*;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import com.yuvraj.ecommerce.requests.OrderItemRequest;
import com.yuvraj.ecommerce.utils.SecurityUtils;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    private final ProductService productService;
    private final SecurityUtils securityUtils;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(ProductService productService, SecurityUtils securityUtils, OrderRepository orderRepository, UserRepository userRepository) {
        this.productService = productService;
        this.securityUtils = securityUtils;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Order createNewOrder(List<OrderItemRequest> orderItemsRequest) {
        User user = securityUtils.getCurrentUser();
        Order order = new Order();
//        List<UserAddress> address = user.getAddresses();
//
//        if(address == null){
//            throw new NotFountException("No address found for user");
//        }


        List<OrderItem> orderItems = orderItemsRequest.stream()
                        .map((oi) -> {
                            Product product = productService.findProductById(oi.getProductId());
                            return new OrderItem(product, oi.getQuantity());
                        }).toList();

        order.createOrder(orderItems);
        user.addOrder(order);
        userRepository.save(user);
        return order;

    }
}
