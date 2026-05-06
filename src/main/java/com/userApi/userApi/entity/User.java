package com.userApi.userApi.entity;

import jakarta.persistence.*;
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
    @Column(name = "email")
    private String email;

    @Getter
    @Column(name = "sex")
    private String sex;

    @Getter
    @Column(name="phone")
    private String phone;

    @Column(name = "password")
    private String password;

//    public User(String name, String email, String sex, String phone, String password){}


}