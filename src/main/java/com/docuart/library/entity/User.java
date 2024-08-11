package com.docuart.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    @NotNull
    private String username;

    @Column(name = "user_surname")
    @NotNull
    @NotBlank
    @Max(30)
    private String userSurname;

    @Email(message = "Email alanı email formatında olmalıdır.")
    private String email;

    @Column(name = "user_password")
    @NotNull
    @Max(12)
    @Min(5)
    private String userPassword;

    private String token;



}
