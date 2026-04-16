package com.yuvraj.ecommerce.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dob;
    private String email;
    private String password;
}
