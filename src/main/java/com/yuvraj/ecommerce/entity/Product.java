package com.yuvraj.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

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
    private double price;
    private int quantity;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("displayOrder ASC ")
    private List<ProductImage> images;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;


    public void addImage(ProductImage img){
        this.images.add(img);
        img.setProduct(this);
    }

    public void addImages(List<MultipartFile> files){

    }
}
