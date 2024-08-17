package com.docuart.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

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
    private String userSurname;

    @Email(message = "Email alanı email formatında olmalıdır.")
    private String email;

    @Column(name = "user_password")
    @NotNull
    @Size(min = 6, message = "Soyad en az 6 karakter olmalıdır.")
    private String userPassword;

    private String token;

    @ManyToMany(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<Role> roles;



}
