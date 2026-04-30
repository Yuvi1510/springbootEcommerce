package com.yuvraj.ecommerce.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    private String name;
    private String brand;
    private String description;
    private double price;
    private int quantity;
    private List<MultipartFile> images;
    private String category;
//    private int storeId;

    // image name will be generated with filename + uuid
    // altText will be product name
    // first image will be primary image


    @Override
    public String toString() {
        return "AddProductRequest{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", images=" + images +
                '}';
    }
}
