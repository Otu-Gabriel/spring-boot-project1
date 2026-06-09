package com.userApi.userApi.dto;

import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Max;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

public record UserRequestDTO(

        @NotBlank(message = "User name must not be empty")
        String name,

        @Email(message = "Email is invalid")
        @NotBlank(message = "Email must not be empty")
        String email,

        @NotBlank(message = "Sex must not be empty")
        String sex,

      @Size(min = 10, max = 10,message = "phone digit must be equal to 10")
        String phone,

       @NotBlank(message = "Must password must not be empty")
       @Size(min = 8, message = "Password must not be less than 8 characters")
       @Pattern(
               regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).+$",
               message = "Password must include special character"
       )
        String password,

        String role


) {}