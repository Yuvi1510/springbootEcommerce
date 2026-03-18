package com.yuvraj.ecommerce.responses;

import com.yuvraj.ecommerce.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private int userId;

    private String firstName;

    private String lastName;

    private LocalDate dob;

    private String email;

    private Address address;

}
