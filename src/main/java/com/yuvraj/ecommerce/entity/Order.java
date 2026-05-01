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

    @Column(name = "sub_total", precision = 10, scale = 2, nullable = false)
    private BigDecimal subTotal;

    @Column(name = "tax_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal taxAmount;

    @Column(name = "delivery_charge", precision = 10, scale = 2, nullable = false)
    private BigDecimal deliveryCharge;

    @Column(name = "total", precision = 10, scale = 2, nullable = false)
    private BigDecimal total;

    @Column(name = "status", length = 50, nullable = false)
    private String status = "accepted";

    @CreationTimestamp
    @Column(name = "date", updatable = false)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private UserAddress userAddress;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();
}