package com.yuvraj.ecommerce.responses;

import com.yuvraj.ecommerce.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreResponseDto {
    private String name;
    private String phone;
    private String email;
    private Address address;
}
