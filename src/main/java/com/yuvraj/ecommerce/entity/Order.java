package com.yuvraj.ecommerce.entity;

import com.yuvraj.ecommerce.enums.OrderStatus;
import com.yuvraj.ecommerce.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`order`")  // Backticks because 'order' is reserved keyword in MySQL
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 50)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "sub_total", nullable = false)
    private double subTotal;

    @Column(name = "tax_amount", nullable = false)
    private double taxAmount;

    @Column(name = "delivery_charge", nullable = false)
    private double deliveryCharge;

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "accepted";

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;


    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();


    public void removeOrderItem(OrderItem orderItem){
        this.orderItemList.remove(orderItem);
        orderItem.setOrder(null);
    }

    public void createOrder(List<OrderItem> orderItemList){
        this.orderStatus = OrderStatus.PENDING;
        this.paymentStatus = PaymentStatus.PENDING;

        for(OrderItem orderItem: orderItemList){
            this.orderItemList.add(orderItem);
            orderItem.setOrder(this);
            this.subTotal += (orderItem.getPriceAtPurchase() * orderItem.getOrderQuantity());
        }

        this.taxAmount = subTotal * 0.13;

        this.total = this.subTotal + this.taxAmount + this.deliveryCharge;

    }
}