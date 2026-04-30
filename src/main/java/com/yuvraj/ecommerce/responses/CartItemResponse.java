package com.yuvraj.ecommerce.responses;

import com.yuvraj.ecommerce.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CartItemResponse {
    private int id;
    private String productName;
    private List<ProductImage> images;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

    public CartItemResponse(int id, String productName, List<ProductImage> images, int quantity, double unitPrice, double total){
        this.id = id;
        this.productName = productName;
        this.images = images;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = total;
    }
}
