package com.example.userservice.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Name must start with an uppercase letter followed by lowercase letters only")
    private String name;

    @NotBlank(message = "Surname is required")
    @Pattern(regexp = "^[A-Z][a-z]*$", message = "Surname must start with an uppercase letter followed by lowercase letters only")
    private String surname;

    @NotBlank(message = "Email is required")
    @Column(unique = true, name = "username")
    private String username;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+7\\d{3}\\d{3}\\d{4}$", message = "Phone number must be in the format +7 *** *** ****")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$", message = "Password must contain at least 8 characters, including at least 1 digit, 1 uppercase letter, 1 lowercase letter, and 1 special character")
    private String password;

    private String role;

}
