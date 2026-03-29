package com.yuvraj.ecommerce.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreDto {
    private int storeId;

    private String name;

    private String phone;

    private String email;

    private Address address;

    private Users user;

}
