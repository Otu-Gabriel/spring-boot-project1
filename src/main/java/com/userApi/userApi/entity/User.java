package com.userApi.userApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @Setter
    @Column(name = "email", unique = true)
    private String email;

    @Getter
    @Column(name = "sex")
    private String sex;

    @Getter
    @Column(name="phone")
    private String phone;

    @Getter
    @Setter
    @Column(unique = true, name = "keycloakId")
    private String keycloakId;


    @Column(name = "role")
    @NotBlank(message = "Role must not be empty")
    @Getter
    @Setter
    private String role;

//    public User(String name, String email, String sex, String phone, String password){}


}