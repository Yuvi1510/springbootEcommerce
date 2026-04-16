package com.yuvraj.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_locked")
    private boolean isLocked;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @OneToOne(mappedBy = "user")
    private Store store;
}
