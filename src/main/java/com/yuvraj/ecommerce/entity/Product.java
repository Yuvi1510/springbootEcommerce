package com.yuvraj.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String name;
    private String slug;
    private String brand;
    private String sku;
    private String description;
    private boolean price;
    private int quantity;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
