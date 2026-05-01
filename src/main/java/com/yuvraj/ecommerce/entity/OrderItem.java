package com.yuvraj.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemId;

    @Column(name = "price_at_purchase")
    private double priceAtPurchase;

    @Column(name = "order_quantity")
    private int orderQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne
    private Product product;

    public OrderItem(Product product, int orderQuantity){
        this.priceAtPurchase = product.getPrice();
        this.orderQuantity = orderQuantity;
        this.product = product;
    }
}
