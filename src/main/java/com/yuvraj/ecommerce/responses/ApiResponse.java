package com.yuvraj.ecommerce.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApiResponse {
    public String message;
    public HttpStatus httpStatus;

}
